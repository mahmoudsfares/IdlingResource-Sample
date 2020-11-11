package com.example.idlingresexperiment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.IdlingResource;

import android.widget.TextView;

/**
 * Gets a text String from the user and displays it back after a while.
 */
public class MainActivity extends AppCompatActivity{

    // The TextView used to display the message inside the Activity.
    private TextView mTextView;
    // The Idling Resource which will be null in production.
    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: RuntimeException: Cannot create an instance of class com.example.idlingresexperiment.MessageDelayerViewModel
        //  Caused by: java.lang.IllegalAccessException: java.lang.Class<com.example.idlingresexperiment.MessageDelayerViewModel>
        //  is not accessible from java.lang.Class<androidx.lifecycle.ViewModelProvider$NewInstanceFactory>
        MessageDelayerViewModel viewModel = new ViewModelProvider(this).get(MessageDelayerViewModel.class);

        mTextView = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(v -> viewModel.processMessage(mIdlingResource));

        MutableLiveData<String> messageData = viewModel.getText();
        messageData.observe(this, s -> mTextView.setText(s));
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
