package com.example.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);

        TestViewModel model = new ViewModelProvider(this).get(TestViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(final String newName) {
                // Update the UI, in this case, a TextView.
                tv.setText(newName);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentName().observe(this, nameObserver);

        String anotherName = "John Doe";
        model.getCurrentName().setValue(anotherName);


    }
}