package com.costar.talkwithidol.ui.activities.eventdetail.mvp;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.questionSubmit.SubmitQuestionResponse;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class AfterSubmitDialog {


    Dialog dialog;
    Activity activity;
    EditText editTextCode;
    Button buttonSend;
    ImageView iv_back;
    TextView tv_message;

    public AfterSubmitDialog(Activity activity) {
        this.activity = activity;
        //dialog = new Dialog(activity);
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.event_question_confirmation);
        iv_back = dialog.findViewById(R.id.iv_back);
        tv_message = dialog.findViewById(R.id.tv_message);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public Observable<Boolean> showDialog() {
        return Observable.create(subscriber -> {

            dialog.show();
            buttonSend.setOnClickListener(v -> {
                subscriber.onNext(false);
                subscriber.onComplete();
                dialog.cancel();
            });


        });
    }

    public String getCode()
    {
        return editTextCode.getText().toString().trim();
    }

    public Observable<Object> sendButtonObservable() {
        return RxView.clicks(buttonSend);
    }

    public Observable<Object> icBackButtonObservable() {
        return RxView.clicks(iv_back);
    }

    public void setDialog(SubmitQuestionResponse submitQuestionResponse){



    }

    public void showDialog(boolean doShow) {
        dialog.setCancelable(false);
        if (doShow)
            dialog.show();
        else
            dialog.dismiss();
    }

}
