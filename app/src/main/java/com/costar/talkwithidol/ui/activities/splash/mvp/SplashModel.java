package com.costar.talkwithidol.ui.activities.splash.mvp;


import android.app.Activity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;
import com.costar.talkwithidol.ui.activities.tutorial.TutorialActivity;

public class SplashModel {

    private final Activity activity;
    private final PreferencesManager preferencesManager;
    private final PadloktNetwork padloktNetwork;
    public SplashModel(Activity activity,  PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;
    }


    public void startLogin() {
        LoginActivity.start(activity);
        activity.finish();
    }

    public void startDashboard() {
       HomePageActivity.start(activity);
        activity.finish();
    }

    public void startTutorial() {
        TutorialActivity.start(activity);
        activity.finish();
    }
    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }


    public String getData(String key) {
        return preferencesManager.get(key);
    }

}
