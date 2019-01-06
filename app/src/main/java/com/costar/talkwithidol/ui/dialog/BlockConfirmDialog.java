package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ReportParams;
import com.costar.talkwithidol.ext.bus.RxBus;

public class BlockConfirmDialog {


    Dialog dialog;
    Activity activity;
    int id, userid;
    Button yesButton, cancelButton;
    String selected;
    ReportParams reportParams;

    public BlockConfirmDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.block_confirm);
        yesButton = dialog.findViewById(R.id.yesButton);
        cancelButton = dialog.findViewById(R.id.cancelButton);

        yesButton.setOnClickListener(v -> {
            RxBus.getInstance().send(reportParams);
            dialog.dismiss();

        });


        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });


    }


    public void showDialog(boolean doShow, ReportParams blockParams) {
        this.reportParams = blockParams;
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


}
