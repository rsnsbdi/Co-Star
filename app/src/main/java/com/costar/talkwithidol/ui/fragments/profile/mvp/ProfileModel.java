package com.costar.talkwithidol.ui.fragments.profile.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.ChangeEmail.ChangeEmailParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileResponse;
import com.costar.talkwithidol.app.network.models.ResendEmailVerification.ResendEmailResponse;
import com.costar.talkwithidol.app.network.models.VerifyMobileResponse.VerifyMobileResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.app.network.models.profile.UpdateProfileParams;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class ProfileModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferenceManager;

    public ProfileModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferenceManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferenceManager = preferenceManager;

    }

    public Observable<SettingsProfileResponse> getProfileObservable(String id, String cookie) {
        return padloktNetwork.getUserProfile(id, cookie);
    }



    public Observable<ChangeMobileResponse> requestVerificationCodeObservable(VerificationParams verificationParams) {
        return padloktNetwork.requestMobileCode(verificationParams ,preferenceManager.get(Constants.COOKIE),preferenceManager.get(Constants.TOKEN));
    }

    public Observable<LoginResponse> updateProfileObservable(UpdateProfileParams updateProfileParams) {
        return padloktNetwork.updateProfile(updateProfileParams , preferenceManager.get(Constants.USERID), preferenceManager.get(Constants.COOKIE),preferenceManager.get(Constants.TOKEN));
    }

    public Observable<VerifyMobileResponse> sendVerificationCodeObservable(ChangeMobileParams  changeMobileParams) {
        return padloktNetwork.verifyMobileCode(changeMobileParams ,preferenceManager.get(Constants.COOKIE),preferenceManager.get(Constants.TOKEN));
    }


    public Observable<LoginResponse> requestChangeEmailObservable(ChangeEmailParams   changeEmailParams) {
        return padloktNetwork.changeEmail(changeEmailParams, preferenceManager.get(Constants.USERID), preferenceManager.get(Constants.COOKIE),preferenceManager.get(Constants.TOKEN));
    }



    public Observable<ResendEmailResponse> resendConfirmationEmailObservable() {
        return padloktNetwork.resendConfrimationEmail(preferenceManager.get(Constants.COOKIE), preferenceManager.get(Constants.USERID), preferenceManager.get(Constants.TOKEN));
    }

    public String getData(String key) {
        return preferenceManager.get(key);
    }


    public void saveData(String key, String value) {
        preferenceManager.save(key, value);
    }



  /*  public Observable<LoginResponse> pushStepsData(BackendPushParams backendPushParams) {
        return padloktNetwork.pushToBackend(backendPushParams);
    }

    public Observable<BackendRetrieveResponse> pullStepsData(BackendRetrieveParams backendRetrieveParams) {
        return padloktNetwork.retrieveFromBackend(backendRetrieveParams);
    }
*/

}


