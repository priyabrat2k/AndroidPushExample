package com.appifiedtech.push.example.main;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    String TAG = getClass().getName();

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, MyGcmRegistrationService.class);
        startService(intent);
        Log.d(TAG,"Started MyGcmRegistrationService");
    }
}
