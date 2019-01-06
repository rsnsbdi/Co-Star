package com.costar.talkwithidol.ui.GCM.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.costar.talkwithidol.ui.GCM.Configs;
import com.costar.talkwithidol.ui.GCM.NotificationUtils;
import com.costar.talkwithidol.ui.activities.splash.SplashActivity;

import org.json.JSONObject;

public class PadloktMessagingService extends FirebaseMessagingService {

    private static final String TAG = PadloktMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    SharedPreferences sharedPreferences;
    PreferenceManager preferenceManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (remoteMessage == null)
            return;
     /*   // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }*/
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            Log.e(TAG, "Message: " + remoteMessage.getNotification().getBody().toString());
           // Log.e(TAG, "Action: " + remoteMessage.getNotification().getClickAction().toString());
            String data = remoteMessage.getData().toString();


            try {
                JSONObject json = new JSONObject(data.replace(" ", "").trim());
               // if(!sharedPreferences.getString(Constants.COOKIE,"").equals("")) {
                    handleDataMessage(json);
              // }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Configs.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {

            String type = json.getString(Configs.TYPE);
            String mode = json.getString(Configs.MODE);
            String id = json.getString(Configs.ID);

            sharedPreferences.edit().putString(Configs.TYPE, type).commit();
            sharedPreferences.edit().putString(Configs.MODE, mode).commit();
            sharedPreferences.edit().putString(Configs.ID, id).commit();


            //whenapp is not in background
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Configs.PUSH_NOTIFICATION);
                //pushNotification.putExtra("message", message);
//                pushNotification.putExtra(Configs.PHONENUMBER, roomnumber);
//                pushNotification.putExtra(Configs.EXPERTNAME, expertname);
//                pushNotification.putExtra(Configs.RSESSION, rsessionid+"");
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

            } else {
                // app is in background, show the notification in notification tray

                Intent resultIntent = new Intent(getApplicationContext(),SplashActivity.class);
                resultIntent.putExtra(Configs.TYPE,type);
                resultIntent.putExtra(Configs.MODE,mode);
                resultIntent.putExtra(Configs.ID,id);
                showNotificationMessage(getApplicationContext(), "", "", "", resultIntent);

            }
        }
      /*  catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }*/
        catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

   /* *//**
     * Showing notification with text and image
     *//*
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }*/
}
