package com.costar.talkwithidol.ui.activities.eulaafterregister.mvp;

import android.app.Activity;

import com.costar.talkwithidol.app.network.EulaParams;
import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;

import io.reactivex.Observable;

/**
 * Created by shreedhar on 12/17/17.
 */

public class EulaModel {

    private final Activity activity;
    private final PadloktNetwork padloktNetwork;
    private final PreferencesManager preferencesManager;


    public void startHome() {
        HomePageActivity.start(activity);
        activity.finish();
        activity.finishAffinity();
    }


    public EulaModel(Activity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;
    }

    public  Observable<EulaResponse> postEulaObservable(EulaParams forgotPasswordParams) {
        return padloktNetwork.postEula(forgotPasswordParams, preferencesManager.get(Constants.COOKIE),preferencesManager.get(Constants.TOKEN));
    }
    public Observable<EulaResponse> getEulaObservable() {
        return padloktNetwork.getUserEula(preferencesManager.get(Constants.COOKIE));
    }
}
