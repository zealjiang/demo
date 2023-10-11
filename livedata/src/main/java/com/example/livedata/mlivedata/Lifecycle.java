package com.example.livedata.mlivedata;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

public abstract class Lifecycle {


    @MainThread
    public abstract void addObserver(@NonNull LifecycleObserver observer);


    @MainThread
    public abstract void removeObserver(@NonNull LifecycleObserver observer);


    @MainThread
    @NonNull
    public abstract androidx.lifecycle.Lifecycle.State getCurrentState();

    @SuppressWarnings("WeakerAccess")
    public enum Event {

        ON_CREATE,

        ON_START,

        ON_RESUME,

        ON_PAUSE,

        ON_STOP,

        ON_DESTROY,

        ON_ANY;

        @Nullable
        public static Event downFrom(@NonNull State state) {
            switch (state) {
                case CREATED:
                    return ON_DESTROY;
                case STARTED:
                    return ON_STOP;
                case RESUMED:
                    return ON_PAUSE;
                default:
                    return null;
            }
        }


        @Nullable
        public static Event downTo(@NonNull State state) {
            switch (state) {
                case DESTROYED:
                    return ON_DESTROY;
                case CREATED:
                    return ON_STOP;
                case STARTED:
                    return ON_PAUSE;
                default:
                    return null;
            }
        }

        @Nullable
        public static Event upFrom(@NonNull State state) {
            switch (state) {
                case INITIALIZED:
                    return ON_CREATE;
                case CREATED:
                    return ON_START;
                case STARTED:
                    return ON_RESUME;
                default:
                    return null;
            }
        }


        @Nullable
        public static Event upTo(@NonNull State state) {
            switch (state) {
                case CREATED:
                    return ON_CREATE;
                case STARTED:
                    return ON_START;
                case RESUMED:
                    return ON_RESUME;
                default:
                    return null;
            }
        }


        @NonNull
        public State getTargetState() {
            switch (this) {
                case ON_CREATE:
                case ON_STOP:
                    return State.CREATED;
                case ON_START:
                case ON_PAUSE:
                    return State.STARTED;
                case ON_RESUME:
                    return State.RESUMED;
                case ON_DESTROY:
                    return State.DESTROYED;
                case ON_ANY:
                    break;
            }
            throw new IllegalArgumentException(this + " has no target state");
        }
    }


    @SuppressWarnings("WeakerAccess")
    public enum State {

        DESTROYED,

        INITIALIZED,

        /**
         *  after {@link android.app.Activity onCreate} call;
         *  right before onStop call.
         */
        CREATED,

        /**
         * after onStart call  right before onPause() call.
         */
        STARTED,

        /**
         * after {@link android.app.Activity onResume} is called.
         */
        RESUMED;

        public boolean isAtLeast(@NonNull State state) {
            return compareTo(state) >= 0;
        }
    }
}
