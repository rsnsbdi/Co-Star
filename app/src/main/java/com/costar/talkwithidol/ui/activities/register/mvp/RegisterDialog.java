package com.costar.talkwithidol.ui.activities.register.mvp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.hbb20.CountryCodePicker;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Date;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RegisterDialog {


    Dialog dialog;
    Activity activity;
    TextView tv_dob, tv_gender, tv_terms_condition;
    CountryCodePicker tv_country;
    Calendar myCalendar;
    DatePickerDialog datePickerDialog;
    Button buttonSend, btn_ignup;
    ImageView backButton;


    public RegisterDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_register_second);
        tv_dob = dialog.findViewById(R.id.ed_dob);
        tv_gender = dialog.findViewById(R.id.ed_gender);
        tv_country = dialog.findViewById(R.id.countryPicker);
        tv_terms_condition = dialog.findViewById(R.id.tv_terms_condition);
        backButton = dialog.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        SpannableString text = new SpannableString("By clicking Sign Up, you agree to our Terms \n and Conditions and have read our Privacy Policy and EULA.");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send("terms");
            }
        };
        text.setSpan(clickableSpan, 38, 60, 0);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send("privacy");
            }
        };
        text.setSpan(clickableSpan1, 79, 93, 0);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send("eula");
            }
        };
        text.setSpan(clickableSpan2, 98, 102, 0);
        text.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.yellow1)), 38, 60, 0);
        text.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.yellow1)), 79, 93, 0);
        text.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.yellow1)), 98, 102, 0);
        tv_terms_condition.setMovementMethod(LinkMovementMethod.getInstance());
        tv_terms_condition.setText(text, TextView.BufferType.SPANNABLE);


        btn_ignup = dialog.findViewById(R.id.btn_signup);

        tv_gender.setOnClickListener(view -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
            // builderSingle.setIcon(R.drawable.ic_launcher);
            builderSingle.setTitle("Select Gender");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("male");
            arrayAdapter.add("female");
            arrayAdapter.add("other");

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    tv_gender.setText(strName);
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        tv_dob.setOnClickListener(v -> {
            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            // date picker dialog
            datePickerDialog = new DatePickerDialog(activity,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        tv_dob.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

            datePickerDialog.show();
        });

    }

    public Observable<Boolean> showDialog() {
        return Observable.create(subscriber -> {

            dialog.show();
            btn_ignup.setOnClickListener(v -> {
                subscriber.onNext(false);
                subscriber.onComplete();
                dialog.cancel();
            });


        });
    }


    public Observable<Object> okButtonObservable() {

        return RxView.clicks(btn_ignup);
    }

//
//    public Observable<Object> termsConditionButtonObservable() {
//
//        return RxView.clicks(tv_terms_condition);
//    }


    public void enableSignupButton(boolean doEnable) {
        btn_ignup.setEnabled(doEnable);
    }


    public Flowable<CharSequence> dobChangeObservable() {
        return RxTextView.textChanges(tv_dob).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> genderChangeObservable() {
        return RxTextView.textChanges(tv_gender).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setDobNameError(String error) {
        tv_dob.setError(error);
    }

    public void setTv_genderError(String error) {
        tv_gender.setError(error);
    }

    public void showDialog(boolean doShow) {
        if (doShow)
            dialog.show();
        else
            dialog.dismiss();
    }

}
