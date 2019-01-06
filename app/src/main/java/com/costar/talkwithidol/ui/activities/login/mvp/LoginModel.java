package com.costar.talkwithidol.ui.activities.login.mvp;

import android.app.Activity;

import com.costar.talkwithidol.app.network.EulaParams;
import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordParams;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordResponse;
import com.costar.talkwithidol.app.network.models.login.LoginParams;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.registerFCM.RegisterFCMIDResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.register.RegisterActivity;

import io.reactivex.Observable;

/**
 * Created by dell on 8/7/2017.
 */

public class LoginModel {

    private final Activity activity;
    private final PadloktNetwork padloktNetwork;
    private final PreferencesManager preferencesManager;

    public LoginModel(Activity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager){
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;
    }


    public void startRegisterActivity() {
        RegisterActivity.start(activity);
    }

    public  Observable<LoginResponse> getUserLoginObservable(LoginParams loginParams) {
        return padloktNetwork.validateUser(loginParams);
    }

    public  Observable<ForgotPasswordResponse> forgotPasswordObservable(ForgotPasswordParams forgotPasswordParams) {
        return padloktNetwork.forgotPassword(forgotPasswordParams);
    }
    public  Observable<EulaResponse> postEulaObservable(EulaParams forgotPasswordParams) {
        return padloktNetwork.postEula(forgotPasswordParams, preferencesManager.get(Constants.COOKIE),preferencesManager.get(Constants.TOKEN));
    }
    public Observable<EulaResponse> getEulaObservable() {
        return padloktNetwork.getUserEula(preferencesManager.get(Constants.COOKIE));
    }

    public  Observable<RegisterFCMIDResponse> getFCMRegisterObservable(String token,String type) {
        return padloktNetwork.registerFCM(token, type,preferencesManager.get(Constants.COOKIE),preferencesManager.get(Constants.TOKEN));
    }



    public String getFCMkey(){
        return preferencesManager.get("regId");
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }

    public void clearData(){
        preferencesManager.clear();
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public void startDashboard() {
        HomePageActivity.start(activity);
        activity.finish();
    }
}