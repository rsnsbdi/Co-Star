package com.costar.talkwithidol.ui.fragments.setting.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.login.LoginParams;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;

import io.reactivex.Observable;


public class SettingModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferenceManager;


    public SettingModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferenceManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferenceManager = preferenceManager;

    }
    public Observable<SettingsProfileResponse> getProfileObservable(String id, String cookie) {
        return padloktNetwork.getUserProfile(id,cookie);
    }

    public  Observable<LoginResponse> getUserLoginObservable(LoginParams loginParams) {
        return padloktNetwork.validateUser(loginParams);
    }

    public String getData(String key) {
        return preferenceManager.get(key);
    }

    public void startLogin() {
        LoginActivity.start(activity);
        activity.finish();
    }
}


