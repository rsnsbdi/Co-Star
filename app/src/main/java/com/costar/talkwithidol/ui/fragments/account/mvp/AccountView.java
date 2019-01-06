package com.costar.talkwithidol.ui.fragments.account.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.ChangePasswordDialog;
import com.costar.talkwithidol.ui.dialog.TermsPrivacyContact;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class AccountView extends FrameLayout {

    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.tv_terms_condition)
    TextView tv_terms_condition;
    @BindView(R.id.tv_privacy_policy)
    TextView tv_privacy_policy;
    @BindView(R.id.tv_contact_detail)
    TextView tv_contact_detail;
    @BindView(R.id.eula)
    TextView eula;


    @BindView(R.id.tv_change_password)
    TextView changePassword;


    PreferencesManager preferencesManager;
    ProgressDialog progressDialog = new ProgressDialog(getContext());
    ShowLoadingDialog showLoadingDialog;
    private AppCompatActivity activity;
    ChangePasswordDialog changePasswordDialog;
    TermsPrivacyContact termsPrivacyContact;


    public AccountView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager, ChangePasswordDialog changePasswordDialog, TermsPrivacyContact termsPrivacyContact) {
        super(activity);
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        this.changePasswordDialog = changePasswordDialog;
        this.termsPrivacyContact = termsPrivacyContact;
        showLoadingDialog = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.setting_account, this);
        ButterKnife.bind(this);
        changePassword.setOnClickListener(v->{
            changePasswordDialog.showDialog(true);
        });



    }

    public Observable<Object> logoutButtonObservable() {
        return RxView.clicks(tv_logout);
    }

    public Observable<Object> termsConditionButtonObservable() {
        return RxView.clicks(tv_terms_condition);
    }
    public Observable<Object> contactDetailButtonObservable() {
        return RxView.clicks(tv_contact_detail);
    }
    public Observable<Object> privacyPolicyButtonObservable() {
        return RxView.clicks(tv_privacy_policy);
    }

    public Observable<Object> eulaButtonObservable() {
        return RxView.clicks(eula);
    }



    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void redirectToLogin() {
        preferencesManager.clear();
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {

            progressDialog.show();
        } else {
            progressDialog.cancel();
        }

    }

    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            showLoadingDialog.showDialog(true);
        } else {
            showLoadingDialog.showDialog(false);
        }

    }




}
