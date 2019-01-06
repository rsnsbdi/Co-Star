package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ReportParams;
import com.costar.talkwithidol.ext.bus.RxBus;

public class ReportDialog {


    Dialog dialog;
    Activity activity;
    int id, userid;
    TextView abusive, harmful, nudity, hate, block;
    String selected;
    ImageView close;
    BlockConfirmDialog blockConfirmDialog;

    public ReportDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        blockConfirmDialog = new BlockConfirmDialog(activity);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.report);
        abusive = dialog.findViewById(R.id.abusive);
        harmful = dialog.findViewById(R.id.harmful);
        nudity = dialog.findViewById(R.id.nudity);
        hate = dialog.findViewById(R.id.hate);
        block = dialog.findViewById(R.id.block);
        close = dialog.findViewById(R.id.iv_close);

        abusive.setOnClickListener(v -> {
            selected = "abusive";
            RxBus.getInstance().send(reportParams());
            dialog.dismiss();

        });


        harmful.setOnClickListener(v -> {
            selected = "harmful";
            RxBus.getInstance().send(reportParams());
            dialog.dismiss();
        });


        nudity.setOnClickListener(v -> {
            selected = "nudity";
            RxBus.getInstance().send(reportParams());
            dialog.dismiss();
        });

        hate.setOnClickListener(v -> {
            selected = "hate";
            RxBus.getInstance().send(reportParams());
            dialog.dismiss();
        });

        block.setOnClickListener(v -> {
//            selected = "block";
            selected = "block";
//            RxBus.getInstance().send(blockParams());
//            dialog.dismiss();
            dialog.dismiss();
            blockConfirmDialog.showDialog(true, blockParams());

        });
        close.setOnClickListener(v -> dialog.dismiss());

    }

    public static void ShowConfirmDialog(Context context, final int personId, final int position) {


    }

    public ReportParams reportParams() {
        return ReportParams.builder().category(selected).id(id).type("comment").build();
    }

    private ReportParams blockParams() {
        return ReportParams.builder().category(selected).id(userid).type("user").build();
    }

    public void showDialog(boolean doShow, int id, int userid) {
        this.id = id;
        this.userid = userid;
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


}
