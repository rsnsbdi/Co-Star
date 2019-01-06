package com.costar.talkwithidol.ui.fragments.profile.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.costar.talkwithidol.R;
import com.hbb20.CountryCodePicker;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ChangeMobileDialog {


    Dialog dialog;
    Activity activity;
    Button sendCode;
    EditText mobileno;
    ImageView close;
    Context context;
    CountryCodePicker countryPicker;

    public ChangeMobileDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.change_mobile);
        sendCode = dialog.findViewById(R.id.btn_send);
        mobileno = dialog.findViewById(R.id.mobileno);
        close = dialog.findViewById(R.id.iv_close);
        countryPicker = dialog.findViewById(R.id.countryPicker);
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
        } else
            dialog.dismiss();
    }

    public Observable<Object> requestVerificationCodeObservable() {
        return RxView.clicks(sendCode);
    }

    public Flowable<CharSequence> mobilenumberCharSequenceFlowable() {
        return RxTextView.textChanges(mobileno).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setMobilenoError(String error) {
        mobileno.setError(error);
    }


}
