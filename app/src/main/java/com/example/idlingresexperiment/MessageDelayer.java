package com.example.idlingresexperiment;

import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.IdlingResource;

/**
 * Takes a String and returns it after a while via a callback.
 * <p>
 * This executes a long-running operation on a different thread that results in problems with
 * Espresso if an {@link IdlingResource} is not implemented and registered.
 */
class MessageDelayer {

    private static final int DELAY_MILLIS = 3000;
    private String text;

    interface DelayerCallback {
        void onDone(String text);
    }

    static MutableLiveData<String> messageData = new MutableLiveData<>();

    /**
     * Takes a String and returns it after {@link #DELAY_MILLIS} via a {@link DelayerCallback}.
     */
    static void processMessage(@Nullable final SimpleIdlingResource idlingResource) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Delay the execution, return message via callback.
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            messageData.setValue("bye world");
            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }
        }, DELAY_MILLIS);
    }

    public MutableLiveData<String> getText(){
        return messageData;
    }
}