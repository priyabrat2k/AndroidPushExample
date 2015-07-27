package com.appifiedtech.push.example.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;


public class MyGcmListenerService extends GcmListenerService {

    String TAG = getClass().getName();
    int countNotification = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String msg = data.getString("message");
        Log.d(TAG,"GCM Message received is "+msg);
        // Notifying to user code goes here
        notifyUser(getApplicationContext(),msg);
    }

    public void notifyUser(Context context,String data){
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra("data", data);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentTitle("New Notification");
        builder.setContentIntent(pendingIntent);
        builder.setContentText(data);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setVibrate(new long[] { 1000, 1000});
        notificationManager.notify(countNotification++, builder.build());
        Log.v(TAG,"count "+countNotification);
    }
}
