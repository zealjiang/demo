package com.example.livedata.mlivedata;

import android.annotation.SuppressLint;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;
import static androidx.lifecycle.Lifecycle.State.INITIALIZED;

public class LifecycleRegistry extends Lifecycle {


    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap =
            new FastSafeIterableMap<>();

    private State mState;

    private final WeakReference<LifecycleOwner> mLifecycleOwner;

    private int mAddingObserverCounter = 0;

    private boolean mHandlingEvent = false;
    private boolean mNewEventOccurred = false;


    private ArrayList<State> mParentStates = new ArrayList<>();
    private final boolean mEnforceMainThread;

    public LifecycleRegistry(@NonNull LifecycleOwner provider) {
        this(provider, true);
    }

    private LifecycleRegistry(@NonNull LifecycleOwner provider, boolean enforceMainThread) {
        mLifecycleOwner = new WeakReference<>(provider);
        mState = INITIALIZED;
        mEnforceMainThread = enforceMainThread;
    }


    @MainThread
    public void setCurrentState(@NonNull State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }

    public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    private void moveToState(State next) {
        if (mState == next) {
            return;
        }
        mState = next;
        if (mHandlingEvent || mAddingObserverCounter != 0) {
            mNewEventOccurred = true;
            // we will figure out what to do on upper level.
            return;
        }
        mHandlingEvent = true;
        sync();
        mHandlingEvent = false;
    }

    private boolean isSynced() {
        if (mObserverMap.size() == 0) {
            return true;
        }
        State eldestObserverState = mObserverMap.eldest().getValue().mState;
        State newestObserverState = mObserverMap.newest().getValue().mState;
        return eldestObserverState == newestObserverState && mState == newestObserverState;
    }

    private State calculateTargetState(LifecycleObserver observer) {
        Map.Entry<LifecycleObserver, ObserverWithState> previous = mObserverMap.ceil(observer);

        State siblingState = previous != null ? previous.getValue().mState : null;
        State parentState = !mParentStates.isEmpty() ? mParentStates.get(mParentStates.size() - 1)
                : null;
        return min(min(mState, siblingState), parentState);
    }

    @Override
    public void addObserver(@NonNull LifecycleObserver observer) {
        enforceMainThreadIfNeeded("addObserver");
        State initialState = mState == DESTROYED ? DESTROYED : INITIALIZED;
        ObserverWithState statefulObserver = new ObserverWithState(observer, initialState);
        LifecycleRegistry.ObserverWithState previous = mObserverMap.putIfAbsent(observer, statefulObserver);

        if (previous != null) {
            return;
        }
        LifecycleOwner lifecycleOwner = mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            // it is null we should be destroyed. Fallback quickly
            return;
        }

        boolean isReentrance = mAddingObserverCounter != 0 || mHandlingEvent;
        State targetState = calculateTargetState(observer);
        mAddingObserverCounter++;
        while ((statefulObserver.mState.compareTo(targetState) < 0
                && mObserverMap.contains(observer))) {
            pushParentState(statefulObserver.mState);
            final Event event = Event.upFrom(statefulObserver.mState);
            if (event == null) {
                throw new IllegalStateException("no event up from " + statefulObserver.mState);
            }
            statefulObserver.dispatchEvent(lifecycleOwner, event);
            popParentState();
            // mState / subling may have been changed recalculate
            targetState = calculateTargetState(observer);
        }

        if (!isReentrance) {
            // we do sync only on the top level.
            sync();
        }
        mAddingObserverCounter--;
    }

    private void popParentState() {
        mParentStates.remove(mParentStates.size() - 1);
    }

    private void pushParentState(State state) {
        mParentStates.add(state);
    }

    @Override
    public void removeObserver(@NonNull LifecycleObserver observer) {
        enforceMainThreadIfNeeded("removeObserver");
        // we consciously decided not to send destruction events here in opposition to addObserver.
        // Our reasons for that:
        // 1. These events haven't yet happened at all. In contrast to events in addObservers, that
        // actually occurred but earlier.
        // 2. There are cases when removeObserver happens as a consequence of some kind of fatal
        // event. If removeObserver method sends destruction events, then a clean up routine becomes
        // more cumbersome. More specific example of that is: your LifecycleObserver listens for
        // a web connection, in the usual routine in OnStop method you report to a server that a
        // session has just ended and you close the connection. Now let's assume now that you
        // lost an internet and as a result you removed this observer. If you get destruction
        // events in removeObserver, you should have a special case in your onStop method that
        // checks if your web connection died and you shouldn't try to report anything to a server.
        mObserverMap.remove(observer);
    }

    /**
     * The number of observers.
     *
     * @return The number of observers.
     */
    @SuppressWarnings("WeakerAccess")
    public int getObserverCount() {
        enforceMainThreadIfNeeded("getObserverCount");
        return mObserverMap.size();
    }

    @NonNull
    @Override
    public State getCurrentState() {
        return mState;
    }

    private void forwardPass(androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        Iterator<Map.Entry<LifecycleObserver, ObserverWithState>> ascendingIterator =
                mObserverMap.iteratorWithAdditions();
        while (ascendingIterator.hasNext() && !mNewEventOccurred) {
            Map.Entry<LifecycleObserver, ObserverWithState> entry = ascendingIterator.next();
            ObserverWithState observer = entry.getValue();
            while ((observer.mState.compareTo(mState) < 0 && !mNewEventOccurred
                    && mObserverMap.contains(entry.getKey()))) {
                pushParentState(observer.mState);
                final Event event = Event.upFrom(observer.mState);
                if (event == null) {
                    throw new IllegalStateException("no event up from " + observer.mState);
                }
                observer.dispatchEvent(lifecycleOwner, event);
                popParentState();
            }
        }
    }

    private void backwardPass(androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        Iterator<Map.Entry<LifecycleObserver, ObserverWithState>> descendingIterator =
                mObserverMap.descendingIterator();
        while (descendingIterator.hasNext() && !mNewEventOccurred) {
            Map.Entry<LifecycleObserver, ObserverWithState> entry = descendingIterator.next();
            ObserverWithState observer = entry.getValue();
            while ((observer.mState.compareTo(mState) > 0 && !mNewEventOccurred
                    && mObserverMap.contains(entry.getKey()))) {
                Event event = Event.downFrom(observer.mState);
                if (event == null) {
                    throw new IllegalStateException("no event down from " + observer.mState);
                }
                pushParentState(event.getTargetState());
                observer.dispatchEvent(lifecycleOwner, event);
                popParentState();
            }
        }
    }

    // happens only on the top of stack (never in reentrance),
    // so it doesn't have to take in account parents
    private void sync() {
        LifecycleOwner lifecycleOwner = mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already"
                    + "garbage collected. It is too late to change lifecycle state.");
        }
        while (!isSynced()) {
            mNewEventOccurred = false;
            // no need to check eldest for nullability, because isSynced does it for us.
            if (mState.compareTo(mObserverMap.eldest().getValue().mState) < 0) {
                backwardPass(lifecycleOwner);
            }
            Map.Entry<LifecycleObserver, ObserverWithState> newest = mObserverMap.newest();
            if (!mNewEventOccurred && newest != null
                    && mState.compareTo(newest.getValue().mState) > 0) {
                forwardPass(lifecycleOwner);
            }
        }
        mNewEventOccurred = false;
    }

    @SuppressLint("RestrictedApi")
    private void enforceMainThreadIfNeeded(String methodName) {
        if (mEnforceMainThread) {
            if (!ArchTaskExecutor.getInstance().isMainThread()) {
                throw new IllegalStateException("Method " + methodName + " must be called on the "
                        + "main thread");
            }
        }
    }

    /**
     * Creates a new LifecycleRegistry for the given provider, that doesn't check
     * that its methods are called on the threads other than main.
     * <p>
     * LifecycleRegistry is not synchronized: if multiple threads access this {@code
     * LifecycleRegistry}, it must be synchronized externally.
     * <p>
     * Another possible use-case for this method is JVM testing, when main thread is not present.
     */
    @VisibleForTesting
    @NonNull
    public static LifecycleRegistry createUnsafe(@NonNull LifecycleOwner owner) {
        return new LifecycleRegistry(owner, false);
    }

    static State min(@NonNull State state1, @Nullable State state2) {
        return state2 != null && state2.compareTo(state1) < 0 ? state2 : state1;
    }

    static class ObserverWithState {
        State mState;
        LifecycleEventObserver mLifecycleObserver;

        ObserverWithState(LifecycleObserver observer, State initialState) {
            mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);
            mState = initialState;
        }

        void dispatchEvent(LifecycleOwner owner, Event event) {
            State newState = event.getTargetState();
            mState = min(mState, newState);
            mLifecycleObserver.onStateChanged(owner, event);
            mState = newState;
        }
    }
}
