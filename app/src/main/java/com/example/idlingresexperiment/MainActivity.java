package com.example.idlingresexperiment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;
import android.view.View;
import android.widget.TextView;

/**
 * Gets a text String from the user and displays it back after a while.
 */
public class MainActivity extends Activity implements View.OnClickListener,
        MessageDelayer.DelayerCallback {

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
    }

    @Override
    public void onClick(View view) {
        MessageDelayer.processMessage("bye world", this, mIdlingResource);
    }

    @Override
    public void onDone(String text) {
        // The delayer notifies the activity via a callback.
        mTextView.setText(text);
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
