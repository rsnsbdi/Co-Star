package com.costar.talkwithidol.ui.activities.login.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.costar.talkwithidol.R;

import io.reactivex.Observable;

public class ForgotPasswordDialog {


    Dialog dialog;
    Activity activity;
    EditText editTextCode;
    Button reset;
    ImageView close;

    public ForgotPasswordDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);

//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.forgotpassword);
        editTextCode = dialog.findViewById(R.id.email);
        reset = dialog.findViewById(R.id.reset);
        close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(v->{
            dialog.dismiss();
        });

    }


    public String getCode() {
        return editTextCode.getText().toString().trim();
    }

    public Observable<Object> sendButtonObservable() {
        return RxView.clicks(reset);
    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
        } else
            dialog.dismiss();
    }

}
