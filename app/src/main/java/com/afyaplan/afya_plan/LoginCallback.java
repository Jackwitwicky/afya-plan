package com.afyaplan.afya_plan;

import android.app.Application;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by witwicky on 02/02/17.
 */

public class LoginCallback extends Application {
    private AuthCallback authCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(Config.TWITTER_KEY, Config.TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
                Toast.makeText(LoginCallback.this, "Successfully verified " + phoneNumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Toast.makeText(LoginCallback.this, "Could not verify this user" , Toast.LENGTH_SHORT).show();
            }
        };
    }

    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}
