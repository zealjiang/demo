package com.example.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }


}
