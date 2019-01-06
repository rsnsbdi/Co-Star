package com.costar.talkwithidol.ui.activities.homepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.GCM.Configs;
import com.costar.talkwithidol.ui.GCM.NotificationUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.homepage.dagger.DaggerHomePageComponent;
import com.costar.talkwithidol.ui.activities.homepage.dagger.HomePageModule;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePagePresenter;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import static com.costar.talkwithidol.ui.activities.login.LoginActivity.sharedPreferences;

/**
 * Created by dell on 8/7/2017.
 */

public class HomePageActivity extends BaseActivity{

    public static String VIDEOID = "";
    @Inject
   HomePageView homePageView;

    @Inject
    HomePagePresenter homePagePresenter;

    String regId;
  public static boolean  backPressed = false;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static void start(Context context){
        context.startActivity(new Intent(context,HomePageActivity.class));
    }

    public static void startLogout(Activity context){

        context.startActivity(new Intent(context,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


    }

    @Override
    public void onBackPressed() {
        homePageView.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerHomePageComponent.builder().appComponent(PadloktApplication.get(HomePageActivity.this).appComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
        setContentView(homePageView);
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


        AppUtils.transparentStatusBar(getWindow());

//        setSupportActionBar(homePageView.getToolbar());

        homePagePresenter.onCreate();



    }


    @Override
    protected void onResume() {
        super.onResume();
        //homePagePresenter.onCreate(getApplicationContext());

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
    protected void onDestroy() {
        super.onDestroy();

    }

}
