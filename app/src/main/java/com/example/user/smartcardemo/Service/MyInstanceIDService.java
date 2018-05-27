package com.example.user.smartcardemo.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        //FirebaseMessaging.getInstance().subscribeToTopic("driverOrder");
        Log.d("FireBase_Token", "Token:"+token);
    }
}
