package com.example.livedata.mlivedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

public interface LifecycleOwner {
    /**
     * Returns the Lifecycle of the provider.
     *
     * @return The lifecycle of the provider.
     */
    @NonNull
    Lifecycle getLifecycle();
}
