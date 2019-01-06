package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;

import com.costar.talkwithidol.R;

public class ShowLoadingDialog {


    Dialog dialog;
    Activity activity;

    public ShowLoadingDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.loading);

    }

    public void showDialog(boolean doShow) {
        if (doShow)
            dialog.show();
        else
            dialog.dismiss();
    }




}