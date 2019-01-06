package com.costar.talkwithidol.ui.activities.eventdetail.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.bus.RxBus;

public class CompleteProfileDialog {


    Dialog dialog;
    Activity activity;
    Button send;
    ImageView close;
    public TextView message;
    int position =3;

    public CompleteProfileDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.complete_profile_dialog);
        send = dialog.findViewById(R.id.sendButton);
        close = dialog.findViewById(R.id.iv_close);
        message = dialog.findViewById(R.id.message);

        close.setOnClickListener(v->{
            dialog.dismiss();
        });
        send.setOnClickListener(v->{
            RxBus.getInstance().send(position);
            dialog.dismiss();
        });


    }



    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
        } else
            dialog.dismiss();
    }

    public void setData( String text){
        message.setText(text);
    }



}
