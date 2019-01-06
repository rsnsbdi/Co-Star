package com.costar.talkwithidol.ui.activities.register.mvp;


import android.app.Activity;

import com.costar.talkwithidol.app.network.EulaParams;
import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.register.RegisterParams;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eulaafterregister.EulaRegisterActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;

import io.reactivex.Observable;

public class RegisterModel {

    private final Activity activity;
    private final PadloktNetwork padloktNetwork;
    private final PreferencesManager preferencesManager;


    public void startHome() {
        HomePageActivity.start(activity);
        activity.finish();
        activity.finishAffinity();
    }

    public void startEula(){
        EulaRegisterActivity.start(activity);
        activity.finish();
        activity.finishAffinity();
    }


    public RegisterModel(Activity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;
    }

    public Observable<TermsContactPrivacyResponse> getTermsConditionObservable() {
        return padloktNetwork.getTermsCondition();
    }

    public Observable<TermsContactPrivacyResponse> getPrivacyPolicyObservable() {
        return padloktNetwork.getPrivacyPolicy();
    }

    public Observable<TermsContactPrivacyResponse> getEulaObservable() {
        return padloktNetwork.getEula();
    }

    public Observable<EulaResponse> getEulaUserObservable() {
        return padloktNetwork.getUserEula(preferencesManager.get(Constants.COOKIE));
    }

    public Observable<VerificationResponse> verificationCode(VerificationParams verificationParams) {
        return padloktNetwork.verifyUser(verificationParams);
    }

    public  Observable<EulaResponse> postEulaObservable(EulaParams forgotPasswordParams) {
        return padloktNetwork.postEula(forgotPasswordParams, preferencesManager.get(Constants.COOKIE),preferencesManager.get(Constants.TOKEN));
    }

    public Observable<LoginResponse> registerUser(RegisterParams registerParams) {
        return padloktNetwork.registerUser(registerParams);
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }

    public void clearData(){
        preferencesManager.clear();
    }

    public void startDashboard() {
        //HomePageActivity.start(activity);
        activity.finish();
    }

    public void finishActivity() {
        activity.finish();
    }
}
