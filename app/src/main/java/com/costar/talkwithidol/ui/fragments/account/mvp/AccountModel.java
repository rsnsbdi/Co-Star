package com.costar.talkwithidol.ui.fragments.account.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordParams;
import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordResponse;
import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;
import com.costar.talkwithidol.app.network.models.logoutResponse.LogoutResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class AccountModel {

    private final PadloktNetwork padloktNetwork;
    PreferencesManager preferencesManager;
    private AppCompatActivity activity;


    public AccountModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }

    public Observable<LogoutResponse> getLogoutObservable() {
        return padloktNetwork.logout(preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<TermsContactPrivacyResponse> getTermsConditionObservable() {
        return padloktNetwork.getTermsCondition();
    }
    public Observable<TermsContactPrivacyResponse> getPrivacyPolicyObservable() {
        return padloktNetwork.getPrivacyPolicy();
    }
    public Observable<TermsContactPrivacyResponse> getContactObservable() {
        return padloktNetwork.getcontact();
    }

    public Observable<TermsContactPrivacyResponse> getEulaObservable() {
        return padloktNetwork.getEula();
    }


    public Observable<ChangePasswordResponse> changePasswordObservable(ChangePasswordParams changePasswordParams) {
        return padloktNetwork.changePassword(changePasswordParams, preferencesManager.get(Constants.USERID), preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }


  /*  public Observable<LoginResponse> pushStepsData(BackendPushParams backendPushParams) {
        return padloktNetwork.pushToBackend(backendPushParams);
    }

    public Observable<BackendRetrieveResponse> pullStepsData(BackendRetrieveParams backendRetrieveParams) {
        return padloktNetwork.retrieveFromBackend(backendRetrieveParams);
    }
*/

}


