package com.costar.talkwithidol.ui.activities.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.GCM.Configs;
import com.costar.talkwithidol.ui.GCM.NotificationUtils;
import com.costar.talkwithidol.ui.activities.login.dagger.DaggerLoginComponent;
import com.costar.talkwithidol.ui.activities.login.dagger.LoginModule;
import com.costar.talkwithidol.ui.activities.login.mvp.LoginPresenter;
import com.costar.talkwithidol.ui.activities.login.mvp.LoginView;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {


    @Inject
    LoginView loginView;

    @Inject
    LoginPresenter loginPresenter;

    String regId;

    public static SharedPreferences sharedPreferences;

    public static void start(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerLoginComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
        setContentView(loginView);

        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Configs.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Configs.TOPIC_GLOBAL);
                    regId = sharedPreferences.getString("regId", null);
                    // Toast.makeText(getApplicationContext(), "Firebase reg id: " + regId, Toast.LENGTH_LONG).show();
                    System.out.println("Firebase reg id:"+regId);

                }
            }
        };
        System.out.println("Firebase reg id:"+sharedPreferences.getString("regId", null));
        loginPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Configs.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Configs.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
