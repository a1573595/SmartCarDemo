package com.example.user.smartcardemo.Service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("FCM", "onMessageReceived:"+remoteMessage.getFrom());
        Log.e("From",remoteMessage.getFrom());
        Log.e("MessageID",remoteMessage.getMessageId());

        /*Map<String, String> map = remoteMessage.getData();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("message",entry.getKey() + "/" + entry.getValue());
        }*/
    }

    @Override
    public void handleIntent(Intent intent) {
        /*FirebaseOptions options = new FirebaseOptions.Builder();
        options.getApiKey()*/
        //add a log, and you'll see the method will be triggered all the time (both foreground and background).
        Log.e( "FireBase", "handleIntent");
        //if you don't know the format of your FCM message,
        //just print it out, and you'll know how to parse it
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            /*for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.e("FCM", "Key: " + key + " Value: " + value);
            }*/

            Log.e("FireBase",bundle.getString("gcm.notification.msg",""));
        }
    }
}
