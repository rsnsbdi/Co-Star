package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;

public class WebSubsDialog {


    Dialog dialog;
    Activity activity;
    TextView tv_denied_message, title, link;

    ImageView iv_close;
    Button tellmemore;

    public WebSubsDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.web_subs);
        dialog.setCancelable(false);
        title = dialog.findViewById(R.id.title);
        tv_denied_message = dialog.findViewById(R.id.tv_denied_message);
        link = dialog.findViewById(R.id.link);
        tellmemore = dialog.findViewById(R.id.tellmemore);
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


    public void setData(SubscribedMessage subscribedMessage) {
        title.setText(subscribedMessage.getTitle());
        tv_denied_message.setText(subscribedMessage.getBody());
        link.setText(subscribedMessage.getLink());
        tellmemore.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(subscribedMessage.getLink()));
            dialog.dismiss();
            activity.startActivity(intent);

        });
    }



}
