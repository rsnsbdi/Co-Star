package com.costar.talkwithidol.ui.activities.eventdetail.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class SubmitQuestionDialog {


    Dialog dialog;
    Activity activity;
    EditText editTextCode;
    Button buttonSend;
    ImageView ic_back,iv_auther_image;
    TextView txt_message;


    public SubmitQuestionDialog(Activity activity) {
        this.activity = activity;
       // dialog = new Dialog(activity);
       dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.event_submit_question);
        editTextCode = dialog.findViewById(R.id.tv_question);
        buttonSend = dialog.findViewById(R.id.submit);
        txt_message = dialog.findViewById(R.id.tv_message);
        iv_auther_image = dialog.findViewById(R.id.iv_auther_image);
        ic_back = dialog.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(false);
            }
        });

//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



    }


    public void showDialogResponse(EventsDetailResponse eventsDetailResponse){
        txt_message.setText(Html.fromHtml(eventsDetailResponse.getData().getSettings().getMessage()));
        showDialog(true);

    }

    public String getCode()
    {
        return editTextCode.getText().toString().trim();
    }

    public Observable<Object> sendButtonObservable() {
        return RxView.clicks(buttonSend);
    }

    public void showDialog(boolean doShow) {
        if (doShow)
            dialog.show();
        else
            dialog.dismiss();
    }

}
