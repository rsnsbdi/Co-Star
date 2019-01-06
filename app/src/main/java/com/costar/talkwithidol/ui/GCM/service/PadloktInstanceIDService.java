package com.costar.talkwithidol.ui.GCM.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.costar.talkwithidol.ui.GCM.Configs;


public class PadloktInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = PadloktInstanceIDService.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Saving reg id to shared preferences
        sharedPreferences.edit().putString("regId", refreshedToken).apply();

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Configs.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

}

