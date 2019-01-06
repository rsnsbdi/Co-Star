package com.costar.talkwithidol.ui.fragments.profile.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.costar.talkwithidol.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ChangeEmailDialog {


    Dialog dialog;
    Activity activity;
    Button send;
    public  EditText email, password;
    ImageView close;

    public ChangeEmailDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.change_email);
        send = dialog.findViewById(R.id.sendButton);
        email = dialog.findViewById(R.id.email);
        password = dialog.findViewById(R.id.password);
        close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(v->{
            dialog.dismiss();
        });


    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }

    public Flowable<CharSequence> emailCharSequenceFlowable() {
        return RxTextView.textChanges(email).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> passwordCharSequenceFlowable() {
        return RxTextView.textChanges(password).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setEmailError(String error) {
        email.setError(error);
    }

    public void setPasswordError(String error) {
        password.setError(error);
    }

    public Observable<Object> sendEmailPasswordCLicked() {
        return RxView.clicks(send);
    }


}
