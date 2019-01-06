package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;

public class TermsPrivacyContact {

    Dialog dialog;
    Activity activity;
    public Button send;
    public TextView tv_title, tv_description;
    ImageView close;

    public TermsPrivacyContact(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity,R.style.DialogStyle);
        dialog.setContentView(R.layout.terms_privacy_contact);

        tv_title = dialog.findViewById(R.id.title);
        tv_description = dialog.findViewById(R.id.description);

        close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(v->{
            dialog.dismiss();
        });


    }


    public void setData(TermsContactPrivacyResponse termsContactPrivacyResponse){
        tv_title.setText(termsContactPrivacyResponse.getData().getTite());
        tv_description.setText(Html.fromHtml(termsContactPrivacyResponse.getData().getDescription()));
        showDialog(true);

    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }




}
