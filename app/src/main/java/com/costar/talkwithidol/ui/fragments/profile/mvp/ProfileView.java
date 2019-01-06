package com.costar.talkwithidol.ui.fragments.profile.mvp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.ui.activities.register.mvp.CodeDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mukesh.countrypicker.CountryPicker;

import java.util.Date;
import java.util.TimeZone;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;


public class ProfileView extends FrameLayout {
    EditText et_firstname, et_lastname;
    TextView et_phone_number;
    TextView et_email;
    TextView et_dob;
    TextView et_gender;
    TextView et_country;
    TextView et_city;
    TextView et_time_zone;
    TextView et_email_verify;
    TextView et_mobile_verify;
    ImageView email_verify_check, mobile_verify_check, location_icon;
    TextView tv_done, tv_done1;
    ProgressDialog progressDialog = new ProgressDialog(getContext());
    ChangeMobileDialog changeMobileDialog;
    CodeDialog codeDialog;
    ChangeEmailDialog changeEmailDialog;
    DatePickerDialog datePickerDialog;
    ShowLoadingDialog showLoadingDialog;
    RelativeLayout loading;
    private AppCompatActivity activity;

    public ProfileView(@NonNull AppCompatActivity activity, ChangeMobileDialog changeMobileDialog, CodeDialog codeDialog, ChangeEmailDialog changeEmailDialog) {
        super(activity);
        this.activity = activity;
        this.changeMobileDialog = changeMobileDialog;
        this.changeEmailDialog = changeEmailDialog;
//        showLoading = new ShowLoadingDialog(activity);

        this.codeDialog = codeDialog;
        inflate(activity, R.layout.setting_profile, this);
        loading = findViewById(R.id.loading_view);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_email = findViewById(R.id.et_email);
        et_dob = findViewById(R.id.et_dob);
        et_gender = findViewById(R.id.et_gender);
        et_country = findViewById(R.id.et_country);
        et_city = findViewById(R.id.et_city);
        et_time_zone = findViewById(R.id.et_time_zone);
        et_email_verify = findViewById(R.id.et_email_verify);
        et_mobile_verify = findViewById(R.id.et_mobile_verify);
        email_verify_check = findViewById(R.id.email_verify_check);
        mobile_verify_check = findViewById(R.id.mobile_verify_check);
        location_icon = findViewById(R.id.location_icon);

        tv_done = findViewById(R.id.tv_done);
        tv_done1 = findViewById(R.id.tv_done1);


        et_phone_number.setOnClickListener(view -> changeMobileDialog.showDialog(true));

        et_email.setOnClickListener(view -> changeEmailDialog.showDialog(true));


        et_gender.setOnClickListener(view -> {
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
                    et_gender.setText(strName);
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(activity,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            // set day of month , month and year value in the edit text
                            et_dob.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        et_country.setOnClickListener(view -> {
            CountryPicker picker = CountryPicker.newInstance("Select Country");
            picker.setListener((name, code, dialCode, flagDrawableResID) -> {
                // Implement your code here

                et_country.setText(code);
                picker.dismiss();
            });
            picker.show(activity.getSupportFragmentManager(), "COUNTRY_PICKER");
        });


    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Object> resendConfirmationObservable() {
        return RxView.clicks(et_email_verify);
    }

    public Observable<Object> updateProfileObservabke() {
        return RxView.clicks(tv_done);
    }

    public Observable<Object> updateProfileObservabke1() {
        return RxView.clicks(tv_done1);
    }

    public VerificationParams verificationParams() {

        return VerificationParams.builder().number(changeMobileDialog.mobileno.getText().toString().trim())
                .country((changeMobileDialog.countryPicker.getSelectedCountryNameCode()))
                .build();

    }

    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {

            progressDialog.show();
        } else {
            progressDialog.cancel();
        }

    }

    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

    public Flowable<CharSequence> firstnameCharSequenceFlowable() {
        return RxTextView.textChanges(et_firstname).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> lastnameCharSequenceFlowable() {
        return RxTextView.textChanges(et_lastname).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> dateOfBirthCharSequenceFlowable() {
        return RxTextView.textChanges(et_dob).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setfirstnameError(String error) {
        et_firstname.setError(error);
    }

    public void setLastNameError(String error) {
        et_lastname.setError(error);
    }

    public void setDobError(String error) {
        et_dob.setError(error);
    }

    public void setProfile(SettingsProfileResponse settingsProfileResponse) {
        et_firstname.setText(settingsProfileResponse.getData().getFirstName());
        et_lastname.setText(settingsProfileResponse.getData().getLastName());
        et_phone_number.setText(settingsProfileResponse.getData().getMobile());
        et_email.setText(settingsProfileResponse.getData().getEmail());
        et_dob.setText(settingsProfileResponse.getData().getDob());
        et_gender.setText(settingsProfileResponse.getData().getGender());
        et_country.setText(settingsProfileResponse.getData().getCountry());
        et_city.setText(settingsProfileResponse.getData().getCountry());
        et_time_zone.setText(settingsProfileResponse.getData().getTimezone());
        location_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_icon_location));
        DrawableCompat.setTint(location_icon.getDrawable(), ContextCompat.getColor(activity, R.color.green1));


        switch (settingsProfileResponse.getData().getEmailVerified()) {
            case "verified":
                et_email_verify.setText(settingsProfileResponse.getData().getEmail());
                et_email_verify.setClickable(false);
                email_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                et_email_verify.setTextColor(getResources().getColor(R.color.green1));
                DrawableCompat.setTint(email_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.green1));
                break;

            case "pending":
                et_email_verify.setText("Register Email");
                et_email_verify.setClickable(false);
                email_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                et_email_verify.setTextColor(getResources().getColor(R.color.red1));
                DrawableCompat.setTint(email_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.red1));
                break;

            case "notverified":
                et_email_verify.setText("Resend Confirmation Email");
                et_email_verify.setClickable(true);
                email_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_resend));
                et_email_verify.setTextColor(getResources().getColor(R.color.yellow1));
                DrawableCompat.setTint(email_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.yellow1));

                break;
        }
        switch (settingsProfileResponse.getData().getMobileVerified()) {
            case "verified":
                et_mobile_verify.setText(settingsProfileResponse.getData().getMobile());
                et_mobile_verify.setClickable(false);
                mobile_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                DrawableCompat.setTint(mobile_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.green1));
                et_mobile_verify.setTextColor(getResources().getColor(R.color.green1));

                break;
            case "pending":
                et_mobile_verify.setText("Register Mobile");
                et_mobile_verify.setClickable(false);
                mobile_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                et_mobile_verify.setTextColor(getResources().getColor(R.color.red1));
                DrawableCompat.setTint(mobile_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.red1));
                break;
            case "notverified":
                et_mobile_verify.setText("Resend Verification Code");
                et_mobile_verify.setClickable(false);
                mobile_verify_check.setImageDrawable(getResources().getDrawable(R.drawable.ic_resend));
                et_mobile_verify.setTextColor(getResources().getColor(R.color.yellow1));
                DrawableCompat.setTint(mobile_verify_check.getDrawable(), ContextCompat.getColor(activity, R.color.yellow1));

                break;
        }

        et_time_zone.setOnClickListener(view -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
            builderSingle.setTitle("Select Timezone");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add(TimeZone.getDefault().getID());
            arrayAdapter.add(settingsProfileResponse.getData().getTimezone());

            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String strName = arrayAdapter.getItem(which);
                et_time_zone.setText(strName);
                dialog.dismiss();
            });
            builderSingle.show();
        });


    }

}
