package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.SubscriptionInfo;

public class IosSubscriberDialog {


    Dialog dialog;
    Activity activity;
    TextView tv_denied_message, title, link;

    ImageView iv_close;
    Button tellmemore;

    public IosSubscriberDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.ios_dialog);
        dialog.setCancelable(false);
        title = dialog.findViewById(R.id.title);
        link = dialog.findViewById(R.id.link);

        tv_denied_message = dialog.findViewById(R.id.tv_denied_message);
        iv_close = dialog.findViewById(R.id.iv_close);
        tellmemore = dialog.findViewById(R.id.tellmemore);
        iv_close.setOnClickListener(view -> dialog.dismiss());


    }


    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


    public void setChannelData(SubscriptionInfo subscriptionInfo) {
        title.setText(subscriptionInfo.getSubscribed_message().getTitle());
        tv_denied_message.setText(subscriptionInfo.getSubscribed_message().getBody());
        link.setText(subscriptionInfo.getSubscribed_message().getLink());
        tellmemore.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(subscriptionInfo.getSubscribed_message().getLink()));
            dialog.dismiss();
            activity.startActivity(intent);

        });
    }


}
