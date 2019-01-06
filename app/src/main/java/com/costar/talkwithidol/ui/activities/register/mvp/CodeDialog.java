package com.costar.talkwithidol.ui.activities.register.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.costar.talkwithidol.R;

import io.reactivex.Observable;

public class CodeDialog {


    Dialog dialog;
    Activity activity;
    EditText editTextCode;
    Button buttonSend;
    ImageView close;

    public CodeDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.verification_code);
        editTextCode = dialog.findViewById(R.id.code);
        buttonSend = dialog.findViewById(R.id.sendButton);
        close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(v->{
            dialog.dismiss();
        });

    }


    public String getCode() {
        return editTextCode.getText().toString().trim();
    }

    public Observable<Object> sendButtonObservable() {
        return RxView.clicks(buttonSend);
    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }

}
