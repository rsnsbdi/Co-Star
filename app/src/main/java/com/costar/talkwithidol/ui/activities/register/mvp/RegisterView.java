package com.costar.talkwithidol.ui.activities.register.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.register.RegisterParams;
import com.costar.talkwithidol.app.network.models.register.VerificationParams;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;
import com.costar.talkwithidol.ui.activities.eulaafterregister.EulaRegisterActivity;
import com.costar.talkwithidol.ui.activities.register.RegisterActivity;
import com.costar.talkwithidol.ui.dialog.TermsPrivacyContact;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.json.JSONObject;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Callback;


@SuppressLint("ViewConstructor")
public class RegisterView extends FrameLayout {

    static Button buttonSend, btn_ignup;
    public ProgressDialog progressDialog = new ProgressDialog(getContext());
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.mobileno)
    EditText mobileno;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_continue)
    Button btn_continue;
    @BindView(R.id.back)
    ImageView backButton;
    Dialog dialog = null;
    Activity activity;
    /* @BindView(R.id.signInButton)
     TextView signInButton;*/
    EditText editTextCode;
    TextView tv_dob, tv_gender;
    CountryCodePicker tv_country;
    Calendar myCalendar;
    DatePickerDialog datePickerDialog;
    RegisterDialog registerDialog;
    CodeDialog codeDialog;
    TermsPrivacyContact termsPrivacyContact;
    private Callback<VerificationResponse> verificationResponseDtoCallback;

    public RegisterView(@NonNull AppCompatActivity activity, RegisterDialog registerDialog, CodeDialog codeDialog, TermsPrivacyContact termsPrivacyContact) {
        super(activity);
        this.activity = activity;
        this.registerDialog = registerDialog;
        this.codeDialog = codeDialog;
        this.termsPrivacyContact = termsPrivacyContact;
        inflate(getContext(), R.layout.activity_register, this);
        ButterKnife.bind(this);
        et_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//        activity.setSupportActionBar(toolbar);
//        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton.setOnClickListener(v -> {
            activity.finish();
        });
        progressDialog.setMessage("Registering your account...");
    }

    public Observable<Object> continueButtonObservable() {
        return RxView.clicks(btn_continue);
    }

    public Observable<Object> sendButtonObservable() {
        return RxView.clicks(buttonSend);
    }


    public Flowable<CharSequence> fullnameChangeObservable() {
        return RxTextView.textChanges(et_name).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> mobilenumberChangeObservable() {
        return RxTextView.textChanges(mobileno).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> emaiCharSequenceFlowable() {
        return RxTextView.textChanges(email).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> passwordChangeObservable() {
        return RxTextView.textChanges(password).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }


    public void enableContinueButton(boolean doEnable) {
        btn_continue.setEnabled(doEnable);
    }

    public void setContinueButton(String text) {
        btn_continue.setText(text);
    }

    /* public Observable<Object> getSignInButtonObservable()
     {
         return RxView.clicks(signInButton);
     }
 */
    public void setFullNameError(String error) {
        et_name.setError(error);
    }

    public void setMobileError(String error) {
        et_name.setError(error);
    }

    public void setPasswordError(String error) {
        password.setError(error);
    }

    public void setEmailError(String error) {
        email.setError(error);
    }

    /*  public void setConfirmPasswordError(String error)
    {
        confirmPassword.setError(error);
    }
*/
    public RegisterParams registerParams() {
        return RegisterParams.builder().name(mobileno.getText().toString().trim())
                .mail(email.getText().toString().trim())
                .pass(password.getText().toString().trim())
                .first_name(et_name.getText().toString().trim())
                // .last_name(et_name.getText().toString().trim())
                .dob(registerDialog.tv_dob.getText().toString().trim())
                .gender(registerDialog.tv_gender.getText().toString().trim())
                .country(((TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE)).getNetworkCountryIso().toUpperCase())
                .timezone(TimeZone.getDefault().getID())
                .registration_source("android")
                .verify_mobile(verifyObject())
                .build();

    }

    public JsonObject verifyObject() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mobileno.getText().toString().trim());
        jsonObject.addProperty("country-code", ((TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE)).getNetworkCountryIso().toUpperCase());
        jsonObject.addProperty("verification_code", codeDialog.editTextCode.getText().toString().trim());
        jsonObject.addProperty("verification_token", RegisterActivity.token);

        return jsonObject;
    }

    public VerificationParams verificationParams() {
        return VerificationParams.builder().number(mobileno.getText().toString().trim())
                .country((registerDialog.tv_country.getSelectedCountryNameCode()))
                .build();

    }


    public String password() {
        return password.getText().toString().trim();
    }


    public String code() {
        return editTextCode.getText().toString().trim();
    }


    public String fullname() {
        return et_name.getText().toString().trim();
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showRegisterDialog() {
        if (!activity.isFinishing()) {
            registerDialog.showDialog(true);
        }
    }

    public void showCodeDialog() {
        if (!activity.isFinishing()) {
            codeDialog.showDialog(true);
        }

    }

    public Observable<Object> signupButtonObservable() {
        return RxView.clicks(btn_ignup);
    }

    public void showLoading(boolean isLoading) {
        if (isLoading)
            progressDialog.show();
        else
            progressDialog.cancel();
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void startEula() {
        codeDialog.showDialog(false);
        registerDialog.showDialog(false);
        Intent i = new Intent(activity, EulaRegisterActivity.class);
        activity.startActivity(i);
        activity.finish();
    }


}
