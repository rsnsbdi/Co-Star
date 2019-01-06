package com.costar.talkwithidol.ui.fragments.preference.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceParams;
import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceResponse;
import com.costar.talkwithidol.app.network.models.UserPreferences.UserPreferencesResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class PreferenceModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    PreferencesManager preferencesManager;


    public PreferenceModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }
    public Observable<UserPreferencesResponse> getUserPreferences() {
        return padloktNetwork.getUserPreferences(preferencesManager.get(Constants.USERID), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<UpdatePreferenceResponse> updatePreferenceResponseObservable(UpdatePreferenceParams updatePreferenceParams) {
        return padloktNetwork.updatePreferenceResponseObservable(updatePreferenceParams, preferencesManager.get(Constants.USERID), preferencesManager.get(Constants.COOKIE), preferencesManager.get(Constants.TOKEN));
    }




}


