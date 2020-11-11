package com.example.idlingresexperiment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.espresso.IdlingResource;
import android.view.View;
import android.widget.TextView;

/**
 * Gets a text String from the user and displays it back after a while.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // The TextView used to display the message inside the Activity.
    private TextView mTextView;

    // The Idling Resource which will be null in production.
    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the listeners for the buttons.
        findViewById(R.id.button).setOnClickListener(this);

        mTextView = findViewById(R.id.textView);

        MessageDelayer md = new MessageDelayer();
        MutableLiveData<String> messageData = md.getText();
        messageData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTextView.setText(s);
            }
        });
    }

    @Override
    public void onClick(View view) {
        MessageDelayer.processMessage(mIdlingResource);
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
