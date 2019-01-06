package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class ConfirmDialog {


    Dialog dialog;
    Activity activity;
    Button confirm;
    TextView title1, title2, desc1, desc2, currentBill, currentCharge, cycle1, cycle2;
    ImageView iv_close;
    RelativeLayout rl_paysummary;

    public ConfirmDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.confirm_dialog);

        confirm = dialog.findViewById(R.id.verify);
        title1 = dialog.findViewById(R.id.tv_bc);
        title2 = dialog.findViewById(R.id.title1);
        desc1 = dialog.findViewById(R.id.tv_desc);
        desc2 = dialog.findViewById(R.id.tv_desc1);
        rl_paysummary = dialog.findViewById(R.id.rl_paysummary);
        currentBill = dialog.findViewById(R.id.tv_available_dollor);
        currentCharge = dialog.findViewById(R.id.tv_available_dollor1);
        cycle1 = dialog.findViewById(R.id.fontTextView);
        cycle2 = dialog.findViewById(R.id.cycle);
        iv_close = dialog.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(view -> showDialog(false));


    }

    public void showDialog(boolean doShow) {
        if (doShow)
            dialog.show();
        else
            dialog.dismiss();
    }

    public void setData(SubscriptionStepOneResponse subscriptionStepOneResponse) {

        title1.setText(subscriptionStepOneResponse.getData().getBillingSummary().getTitle());
        desc1.setText(subscriptionStepOneResponse.getData().getBillingSummary().getBillingMessage());
        currentBill.setText(subscriptionStepOneResponse.getData().getBillingSummary().getCurrentBill() + " " + subscriptionStepOneResponse.getData().getBillingSummary().getCycle());

        if (subscriptionStepOneResponse.getData().getPaynowSumary() != null) {
            title2.setText(subscriptionStepOneResponse.getData().getPaynowSumary().getTitle());
            desc2.setText(subscriptionStepOneResponse.getData().getPaynowSumary().getPaynowMessage());
            currentCharge.setText(subscriptionStepOneResponse.getData().getPaynowSumary().getCurrentCharge() + " " + subscriptionStepOneResponse.getData().getPaynowSumary().getCycle());
        } else {
            rl_paysummary.setVisibility(View.GONE);
        }


    }

    public Observable<Object> confirmButtonObservable() {
        return RxView.clicks(confirm);
    }


}