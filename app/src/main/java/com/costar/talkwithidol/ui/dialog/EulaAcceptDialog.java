package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class EulaAcceptDialog {

    public TextView tv_title, tv_description;
    Dialog dialog;
    Activity activity;
    Button accept, decline;
    PreferencesManager preferencesManager;

    public EulaAcceptDialog(Activity activity, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.eula_dialog);

        tv_title = dialog.findViewById(R.id.title);
        tv_description = dialog.findViewById(R.id.description);
        accept = dialog.findViewById(R.id.btn_agree);
        decline = dialog.findViewById(R.id.btn_decline);
        decline.setOnClickListener(v -> {
            preferencesManager.clear();
            dialog.dismiss();
        });
    }

    public Observable<Object> acceptButtonObservable() {
        return RxView.clicks(accept);
    }

    public void setData(EulaResponse termsContactPrivacyResponse) {
        tv_title.setText(termsContactPrivacyResponse.getData().getTite());
        tv_description.setText(Html.fromHtml(termsContactPrivacyResponse.getData().getDescription()));
    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


}
