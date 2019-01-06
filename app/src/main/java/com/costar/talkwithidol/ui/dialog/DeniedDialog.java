package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class DeniedDialog {


    Dialog dialog;
    Activity activity;
    Button btn_free_trial, btn_subscribe;
    TextView tv_denied_message, statictext1, statictext2, statictext3;

    ImageView iv_close;

    public DeniedDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.access_denieds);
        dialog.setCancelable(false);
        btn_free_trial = dialog.findViewById(R.id.btn_free_trial);
        btn_subscribe = dialog.findViewById(R.id.btn_subscribe_now);
        tv_denied_message = dialog.findViewById(R.id.tv_denied_message);
        iv_close = dialog.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

    }


    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }

    public Observable<Object> freeTrialButtonObservable() {
        return RxView.clicks(btn_free_trial);
    }

    public Observable<Object> subscribeNowButtonObservable() {
        return RxView.clicks(btn_subscribe);
    }

    public void setDialogData(String message, String freeTrialName, String SubscribeName, String code) {


        tv_denied_message.setText(message);
        btn_free_trial.setText(freeTrialName);
        btn_subscribe.setText(SubscribeName);
        if (code.equals("trial_nooffer") || code.equals("trial_expired")) {
            btn_free_trial.setClickable(false);
        }

    }


}
