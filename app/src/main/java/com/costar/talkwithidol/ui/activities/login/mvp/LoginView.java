package com.costar.talkwithidol.ui.activities.login.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ui.dialog.EulaAcceptDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by dell on 8/7/2017.
 */

public class LoginView extends FrameLayout {


    @BindView(R.id.registerButton)
    TextView registerButton;

    @BindView(R.id.activity_login_email)
    EditText emailText;

    @BindView(R.id.activity_login_password)
    EditText passwordText;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.txt_forgotpassword)
    TextView forgotPassword;

    ProgressDialog progressDialog = new ProgressDialog(getContext());
    ForgotPasswordDialog forgotPasswordDialog;
    EulaAcceptDialog eulaAcceptDialog;

    public LoginView(@NonNull Context context, ForgotPasswordDialog forgotPasswordDialog, EulaAcceptDialog eulaAcceptDialog) {
        super(context);
        this.forgotPasswordDialog = forgotPasswordDialog;
        this.eulaAcceptDialog = eulaAcceptDialog;
        inflate(context, R.layout.activity_login, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Logging in...");

        forgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordDialog.showDialog(true);
            }
        });

    }


    public Observable<Object> loginButtonObservable() {
        return RxView.clicks(loginButton);
    }

    public Observable<Object> registerButtonObservable() {
        return RxView.clicks(registerButton);
    }


    public Flowable<CharSequence> emailAddressChangeObservable() {
        return RxTextView.textChanges(emailText).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> passwordChangeObservable() {
        return RxTextView.textChanges(passwordText).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setEmailError(String error) {
        emailText.setError(error);
    }

    public void setPasswordError(String error) {
        passwordText.setError(error);
    }

    public String emailAddress() {
        return emailText.getText().toString().trim();
    }

    public String password() {
        return passwordText.getText().toString().trim();
    }

    public void enableLoginButton(boolean doEnable) {
        loginButton.setEnabled(doEnable);
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            progressDialog.show();
        } else {
            progressDialog.cancel();
        }

    }


}
