package com.appifiedtech.push.example.main;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
public class MyGcmRegistrationService extends IntentService {

    String TAG = getClass().getName();
    public MyGcmRegistrationService() {
        super("GCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"inside onhandle of "+TAG);
        // Again call sending functions
    }
}
