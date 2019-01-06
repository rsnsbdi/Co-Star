package com.costar.talkwithidol.ui.fragments.profile.mvp;


import android.app.Activity;
import android.telephony.TelephonyManager;

import com.costar.talkwithidol.app.network.models.ChangeEmail.ChangeEmailParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileParams;
import com.costar.talkwithidol.app.network.models.ChangeMobileResponse.ChangeMobileResponse;
import com.costar.talkwithidol.app.network.models.ResendEmailVerification.ResendEmailResponse;
import com.costar.talkwithidol.app.network.models.VerifyMobileResponse.VerifyMobileResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.app.network.models.profile.UpdateProfileParams;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.RxEvent;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;

public class ProfilePresenter {

    private static String verificationToken = null;
    private final ProfileView profileView;
    private final ProfileModel profileModel;
    Activity activity;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    //private FitnessUtils fitnessUtils;


    public ProfilePresenter(ProfileView profileView, ProfileModel profileModel, Activity activity
    ) {
        this.activity = activity;
        this.profileView = profileView;
        this.profileModel = profileModel;

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression ="^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void onCreateView() {

        getUserDetails();
        getClickForResend();
        requestVerificationCode();
        sendCode();
        changeEmailClicked();
        doneClicked();
        doneClicked1();
        formValidation();
        formValidationProfile();
//        formValidationMobile();


    }

    private void formValidationProfile() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    profileView.tv_done.setEnabled(true);
                }
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t, "There was an error");
            }

            @Override
            public void onComplete() {
                Timber.d("Completed");
            }
        };


        Flowable.combineLatest(profileView.firstnameCharSequenceFlowable(),
                profileView.lastnameCharSequenceFlowable(),
                profileView.dateOfBirthCharSequenceFlowable(),


                (firstname, lastname, dob) -> {

                    boolean isFirstnameValid = !isEmpty(profileView.et_firstname.getText().toString());
                    if (!isFirstnameValid)
                        profileView.setfirstnameError("First name is Empty");

                    boolean isLastNameValid = !isEmpty(profileView.et_lastname.getText().toString());
                    if (!isLastNameValid)
                        profileView.setLastNameError("Last name is Empty");


                    boolean isDobValid = !isEmpty(profileView.et_dob.getText().toString());
                    if (!isDobValid)
                        profileView.setDobError("Date of birth is Empty");

                    return isFirstnameValid && isLastNameValid  && isDobValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    profileView.changeEmailDialog.send.setEnabled(true);
                }
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t, "There was an error");
            }

            @Override
            public void onComplete() {
                Timber.d("Completed");
            }
        };


        Flowable.combineLatest(profileView.changeEmailDialog.emailCharSequenceFlowable(),
                profileView.changeEmailDialog.passwordCharSequenceFlowable(),

                (email, password) -> {

                    boolean isEmailValid = !isEmpty(profileView.changeEmailDialog.email.getText().toString().trim());
                    if (!isEmailValid)
                        profileView.changeEmailDialog.setEmailError("Email is Empty");


                    boolean isPasswordValid = !isEmpty(profileView.changeEmailDialog.password.getText().toString().trim());

                    if (!isPasswordValid)
                        profileView.changeEmailDialog.setEmailError("Password is Empty");


                    return isPasswordValid && isEmailValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

//    private void formValidationMobile() {
//
//
//        //call when it is subscribe
//        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
//            @Override
//            public void onNext(Boolean isFormValid) {
//                if (isFormValid) {
//
//                    profileView.changeMobileDialog.sendCode.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Timber.e(t, "There was an error");
//            }
//
//            @Override
//            public void onComplete() {
//                Timber.d("Completed");
//            }
//        };
//
//
//        Flowable.combineLatest(profileView.changeMobileDialog.mobilenumberCharSequenceFlowable(),
//
//                (mobile) -> {
//
//                    boolean isMobileValid = !isEmpty(profileView.changeMobileDialog.mobileno.getText().toString().trim());
//                    if (!isMobileValid)
//                        profileView.changeMobileDialog.setMobilenoError("Mobile number is empty");
//
//
//
//                    return isMobileValid;
//
//                }).subscribe(disposableObserver);
//        compositeDisposable.add(disposableObserver);
//    }

    public void onDestroyView() {
        compositeDisposable.clear();
    }

    private void getUserDetails() {
      //  profileView.showLoading(true);

        DisposableObserver<SettingsProfileResponse> disposableObserver = new DisposableObserver<SettingsProfileResponse>() {

            @Override
            public void onNext(SettingsProfileResponse settingsProfileResponse) {
                if (settingsProfileResponse.getSuccess()&&settingsProfileResponse.getData()!=null) {
                    profileView.setProfile(settingsProfileResponse);
                    profileModel.saveData(Constants.FIRSTNAME, settingsProfileResponse.getData().getFirstName());
                    profileModel.saveData(Constants.LASTNAME, settingsProfileResponse.getData().getLastName());
                    profileModel.saveData(Constants.EMAIL, settingsProfileResponse.getData().getEmail());
                    profileModel.saveData(Constants.REFERENCE, settingsProfileResponse.getData().getReference());
                    profileModel.saveData(Constants.MOBILE, settingsProfileResponse.getData().getMobile());
                    profileModel.saveData(Constants.COUNTRY, settingsProfileResponse.getData().getCountry());
                } else {
                    profileView.showMessage(settingsProfileResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // profileView.showMessage("Time Out");
                    getUserDetails();

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");

                } else {
                    profileView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
               // profileView.showLoading(false);
            }
        };

        profileModel.getProfileObservable(profileModel.getData(Constants.USERID), profileModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getClickForResend() {
        DisposableObserver<Object> disposableObserver = getResendClickObsevable();
        profileView.resendConfirmationObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Object> getResendClickObsevable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object object) {
                resend();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Timber.e("Clicked");
            }
        };
    }


    private void resend() {

        DisposableObserver<ResendEmailResponse> disposableObserver = new DisposableObserver<ResendEmailResponse>() {

            @Override
            public void onNext(ResendEmailResponse resendEmailResponse) {
                if (resendEmailResponse.getSuccess()) {
                    profileView.showMessage(resendEmailResponse.getData().getMessage());
                } else {
                    profileView.showMessage(resendEmailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");

                } else {
                    profileView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        profileModel.resendConfirmationEmailObservable()
//                .doOnNext(__ -> profileView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnEach(__ -> profileView.showLoading(false))
                .subscribe(disposableObserver);
    }

    private void requestVerificationCode() {

        DisposableObserver<ChangeMobileResponse> disposableObserver = new DisposableObserver<ChangeMobileResponse>() {

            @Override
            public void onNext(ChangeMobileResponse changeMobileResponse) {
                if (changeMobileResponse.getSuccess()) {
                    verificationToken = changeMobileResponse.getData().getVerificationToken();
                    profileView.codeDialog.showDialog(true);
                } else {
                    profileView.showMessage(changeMobileResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");

                } else {
                    profileView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        profileView.changeMobileDialog.requestVerificationCodeObservable()
                .doOnNext(__ -> profileView.showProgressDialog(true))
                .map(__ -> profileView.verificationParams())
                .observeOn(Schedulers.io())
                .switchMap(profileModel::requestVerificationCodeObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> profileView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void sendCode() {

        DisposableObserver<VerifyMobileResponse> disposableObserver = new DisposableObserver<VerifyMobileResponse>() {

            @Override
            public void onNext(VerifyMobileResponse verifyMobileResponse) {
                if (verifyMobileResponse.getSuccess()) {
                    if (verifyMobileResponse.getData().getVerified()) {
                        getUserDetails();
                        profileView.showMessage("Mobile is verified!");
                    } else {
                        profileView.showMessage("Mobile not verified!");
                    }
                    profileView.changeMobileDialog.showDialog(false);
                    profileView.codeDialog.showDialog(false);

                } else {
                    profileView.showMessage(verifyMobileResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");

                } else {
                    profileView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        profileView.codeDialog.sendButtonObservable()
                .doOnNext(__ -> profileView.showProgressDialog(true))
                .map(__ -> changeMobileParams())
                .observeOn(Schedulers.io())
                .switchMap(profileModel::sendVerificationCodeObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> profileView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void changeEmailClicked() {
        profileView.progressDialog.setMessage("Processing");
        DisposableObserver<LoginResponse> disposableObserver = new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(LoginResponse changePasswordResponse) {
                if (changePasswordResponse.getSuccess()) {
                    profileView.changeEmailDialog.showDialog(false);
                    profileView.showMessage("Your email has been successfully updated!");
                    profileModel.saveData(Constants.COOKIE, changePasswordResponse.getData().getSessionName() + "=" + changePasswordResponse.getData().getSessid());
                    profileModel.saveData(Constants.TOKEN, changePasswordResponse.getData().getToken());
                    profileModel.saveData(Constants.USERID, changePasswordResponse.getData().getUid());
                    profileModel.saveData(Constants.FIRSTNAME, changePasswordResponse.getData().getFirstName());
                    profileModel.saveData(Constants.LASTNAME, changePasswordResponse.getData().getLastName());
                    profileModel.saveData(Constants.EMAIL, changePasswordResponse.getData().getMail());
                    profileModel.saveData(Constants.REFERENCE, changePasswordResponse.getData().getReference());
                    profileModel.saveData(Constants.MOBILE, changePasswordResponse.getData().getMobile());
                    profileModel.saveData(Constants.COUNTRY, changePasswordResponse.getData().getCountry());
                    getUserDetails();
                } else {
                    profileView.showMessage(changePasswordResponse.getMessage());
                    profileView.changeEmailDialog.showDialog(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");

                } else {
                    profileView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        profileView.changeEmailDialog.sendEmailPasswordCLicked()
                .doOnNext(__ -> profileView.showProgressDialog(true))
                .map(__ -> changeEmailParams())
                .observeOn(Schedulers.io())
                .switchMap(profileModel::requestChangeEmailObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> profileView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void doneClicked() {

        DisposableObserver<LoginResponse> disposableObserver = new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse.getSuccess()) {
                    profileView.changeEmailDialog.showDialog(false);

                    RxBus.getInstance().send(new RxEvent.LoginApiResponse(loginResponse));

                    profileView.showMessage("Your profile has been successfully updated!");
                    profileModel.saveData(Constants.USERIMAGE, loginResponse.getData().getAvatarurl());
                    profileModel.saveData(Constants.COOKIE, loginResponse.getData().getSessionName() + "=" + loginResponse.getData().getSessid());
                    profileModel.saveData(Constants.TOKEN, loginResponse.getData().getToken());
                    profileModel.saveData(Constants.USERID, loginResponse.getData().getUid());
                    profileModel.saveData(Constants.FIRSTNAME, loginResponse.getData().getFirstName());
                    profileModel.saveData(Constants.LASTNAME, loginResponse.getData().getLastName());
                    profileModel.saveData(Constants.EMAIL, loginResponse.getData().getMail());
                    profileModel.saveData(Constants.REFERENCE, loginResponse.getData().getReference());
                    profileModel.saveData(Constants.MOBILE, loginResponse.getData().getMobile());
                    profileModel.saveData(Constants.COUNTRY, loginResponse.getData().getCountry());
                    profileModel.saveData(Constants.AVATERIMAGE, loginResponse.getData().getAvatarurl());
                    profileModel.saveData(Constants.PROFILEIMAGESetting, loginResponse.getData().getProfile_url());
                    getUserDetails();

                } else {
                    profileView.showMessage(loginResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                   // profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    profileView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        profileView.updateProfileObservabke()
                .doOnNext(__ -> profileView.showProgressDialog(true))
                .map(__ -> updateProfileParams())
                .observeOn(Schedulers.io())
                .switchMap(profileModel::updateProfileObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> profileView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void doneClicked1() {

        DisposableObserver<LoginResponse> disposableObserver = new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse.getSuccess()) {
                    profileView.changeEmailDialog.showDialog(false);

                    RxBus.getInstance().send(new RxEvent.LoginApiResponse(loginResponse));

                    profileView.showMessage("Your profile has been successfully updated!");
                    profileModel.saveData(Constants.USERIMAGE, loginResponse.getData().getAvatarurl());
                    profileModel.saveData(Constants.COOKIE, loginResponse.getData().getSessionName() + "=" + loginResponse.getData().getSessid());
                    profileModel.saveData(Constants.TOKEN, loginResponse.getData().getToken());
                    profileModel.saveData(Constants.USERID, loginResponse.getData().getUid());
                    profileModel.saveData(Constants.FIRSTNAME, loginResponse.getData().getFirstName());
                    profileModel.saveData(Constants.LASTNAME, loginResponse.getData().getLastName());
                    profileModel.saveData(Constants.EMAIL, loginResponse.getData().getMail());
                    profileModel.saveData(Constants.REFERENCE, loginResponse.getData().getReference());
                    profileModel.saveData(Constants.MOBILE, loginResponse.getData().getMobile());
                    profileModel.saveData(Constants.COUNTRY, loginResponse.getData().getCountry());
                    profileModel.saveData(Constants.AVATERIMAGE, loginResponse.getData().getAvatarurl());
                    profileModel.saveData(Constants.PROFILEIMAGESetting, loginResponse.getData().getProfile_url());
                    getUserDetails();
                } else {
                    profileView.showMessage(loginResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    profileView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // profileView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    profileView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    profileView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        profileView.updateProfileObservabke1()
                .doOnNext(__ -> profileView.showProgressDialog(true))
                .map(__ -> updateProfileParams())
                .observeOn(Schedulers.io())
                .switchMap(profileModel::updateProfileObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> profileView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }



    public ChangeMobileParams changeMobileParams() {

        return ChangeMobileParams.builder().number(profileView.changeMobileDialog.mobileno.getText().toString().trim())
                .country(((TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE)).getNetworkCountryIso().toUpperCase())
                .verification_token(verificationToken)
                .verification_code(profileView.codeDialog.getCode().trim())
                .build();

    }


    public ChangeEmailParams changeEmailParams() {
        return ChangeEmailParams.builder().mail(profileView.changeEmailDialog.email.getText().toString().trim())
                .currentPassword(profileView.changeEmailDialog.password.getText().toString().trim()).build();
    }


    public UpdateProfileParams updateProfileParams() {
        return UpdateProfileParams.builder().first_name(profileView.et_firstname.getText().toString().trim())
                .last_name(profileView.et_lastname.getText().toString().trim())
                .gender(profileView.et_gender.getText().toString().toLowerCase().trim())
                .dob(profileView.et_dob.getText().toString().trim())
                .timezone(profileView.et_time_zone.getText().toString().trim())
                .country(profileView.et_country.getText().toString().trim())
                .build();
    }


    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
