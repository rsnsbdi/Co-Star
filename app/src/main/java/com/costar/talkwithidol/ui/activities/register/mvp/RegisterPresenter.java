package com.costar.talkwithidol.ui.activities.register.mvp;

import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.register.verification.VerificationResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.register.RegisterActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;

public class RegisterPresenter {

    private final RegisterView registerView;
    private final RegisterModel registerModel;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RegisterPresenter(RegisterView registerView, RegisterModel registerModel) {
        this.registerView = registerView;
        this.registerModel = registerModel;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void onCreate() {
        formValidation();
        continueClicked();
        formValidation1();
        sendRegisterClicked();
        sendVerificationClicked();

        RxBus.getInstance().toObservable().subscribe(o -> {
            if (o instanceof String) {
                switch ((String) o) {
                    case "privacy":
                        privacyClicked();
                        break;

                    case "eula":
                        eulaClicked();
                        break;

                    case "terms":
                        termsConditionClicked();
                        break;
                }
            }
        });

    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

    public void continueClicked() {


        registerView.continueButtonObservable()
                .subscribe(o -> registerView.showRegisterDialog());
    }

    //verification
    public void sendVerificationClicked() {

        DisposableObserver<VerificationResponse> disposableObserver = new DisposableObserver<VerificationResponse>() {

            @Override
            public void onNext(VerificationResponse verificationResponse) {

                registerView.registerDialog.enableSignupButton(true);
                if (verificationResponse.getSuccess() && verificationResponse.getData() != null) {

                    registerModel.saveData("verificationtoken", verificationResponse.getData().getVerificationToken());
                    RegisterActivity.token = verificationResponse.getData().getVerificationToken();
                    registerView.showCodeDialog();
                } else {
                    registerView.showMessage(verificationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                    registerView.registerDialog.enableSignupButton(true);
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");
                    registerView.registerDialog.enableSignupButton(true);

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");
                    registerView.registerDialog.enableSignupButton(true);
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    registerView.showMessage(e.getMessage());
                    registerView.registerDialog.enableSignupButton(true);
                    // loginModel.startDashboard();
                }
            }

            @Override
            public void onComplete() {
                registerView.registerDialog.enableSignupButton(true);
            }
        };


        registerView.registerDialog.okButtonObservable()
                .doOnNext(__ -> registerView.showLoading(true))
                .doOnNext(__ -> registerView.registerDialog.enableSignupButton(false))
                .map(__ -> registerView.verificationParams())
                .observeOn(Schedulers.io())
                .switchMap(registerModel::verificationCode)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> registerView.showLoading(false))
                .subscribe(disposableObserver);
    }

    //regitering
    public void sendRegisterClicked() {

        DisposableObserver<LoginResponse> disposableObserver = new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(LoginResponse loginResponse) {

                if (loginResponse.getSuccess() == true) {
                    registerModel.saveData(Constants.COOKIE, loginResponse.getData().getSessionName() + "=" + loginResponse.getData().getSessid());
                    registerModel.saveData(Constants.TOKEN, loginResponse.getData().getToken());
                    registerModel.saveData(Constants.USERID, loginResponse.getData().getUid());
                    registerModel.saveData(Constants.USERIMAGE, loginResponse.getData().getAvatarurl());

                    registerModel.saveData(Constants.FIRSTNAME, loginResponse.getData().getFirstName());
                    registerModel.saveData(Constants.LASTNAME, loginResponse.getData().getLastName());
                    registerModel.saveData(Constants.EMAIL, loginResponse.getData().getMail());
                    registerModel.saveData(Constants.REFERENCE, loginResponse.getData().getReference());
                    registerModel.saveData(Constants.MOBILE, loginResponse.getData().getMobile());
                    registerModel.saveData(Constants.COUNTRY, loginResponse.getData().getCountry());

                    registerModel.saveData(Constants.AVATERIMAGE, loginResponse.getData().getAvatarurl());
                    registerModel.saveData(Constants.PROFILEIMAGESetting, loginResponse.getData().getProfile_url());
                    registerView.codeDialog.showDialog(false);
                    registerView.registerDialog.showDialog(false);

//                    registerModel.startHome();
                    registerView.startEula();
                } else {
                    registerView.showMessage(loginResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    registerView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };


        registerView.codeDialog.sendButtonObservable()
                .doOnNext(__ -> registerView.showLoading(true))
                .map(__ -> registerView.registerParams())
                .observeOn(Schedulers.io())
                .switchMap(registerModel::registerUser)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> registerView.showLoading(false))
                .subscribe(disposableObserver);
    }


    private void formValidation() {

        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid)
                    registerView.enableContinueButton(true);
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
        Flowable.combineLatest(
                registerView.fullnameChangeObservable(),
                registerView.mobilenumberChangeObservable(),
                registerView.passwordChangeObservable(),

                (newFullName, newMobile, newPassword) -> {

                    boolean isFullNameValid = !isEmpty(newFullName);
                    if (!isFullNameValid)
                        registerView.setFullNameError("Please fill the fullname");

                    boolean isMobileValid = !isEmpty(newMobile);
                    if (!isFullNameValid)
                        registerView.setMobileError("Please fill the mobile no");

                    boolean isPasswordValid = !isEmpty(newPassword) && newPassword.length() >= 8 && newPassword.toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})");
                    if (!isPasswordValid)
                        registerView.setPasswordError("Password must contains atleast 8 character and must contain one capital letter and one small letter and number ");


                    return isFullNameValid && isMobileValid && isPasswordValid;

                }
        ).subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }

    private void formValidation1() {

        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid)
                    registerView.registerDialog.enableSignupButton(true);
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
        Flowable.combineLatest(
                registerView.registerDialog.dobChangeObservable(),
                registerView.registerDialog.genderChangeObservable(),

                (dob, gender) -> {

                    boolean isFullNameValid = !isEmpty(dob);
                    if (!isFullNameValid)
                        registerView.setFullNameError("Please select date of birth");

                    boolean isMobileValid = !isEmpty(gender);
                    if (!isFullNameValid)
                        registerView.setMobileError("Please fill the mobile no");


                    return isFullNameValid && isMobileValid;

                }
        ).subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }


    private void registerButtonClicked() {
        DisposableObserver<LoginResponse> disposableObserver = getRegisterButtonObserver();
        getRegisterButtonObservable()
                .subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }

    private Observable<LoginResponse> getRegisterButtonObservable() {
        return registerView.continueButtonObservable()
                .doOnNext(__ -> registerView.showLoading(true))
                .map(__ -> registerView.registerParams())
                .observeOn(Schedulers.io())
                .switchMap(registerModel::registerUser)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> registerView.showLoading(false));
    }

    private DisposableObserver<LoginResponse> getRegisterButtonObserver() {
        return new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {


                if (loginResponse.getSuccess()) {
                    registerModel.saveData(Constants.EMAIL_ADDRESS, registerView.fullname());
                    registerModel.startDashboard();
                } else {

                    registerView.showMessage(loginResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");

                } else {
                    registerView.showMessage(e.getMessage());
                }
                //registerButtonClicked();
            }

            @Override
            public void onComplete() {
                Timber.e("Completed");
            }
        };
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }


//    //terms and condition
//    private void onTermsConditionClicked() {
//        DisposableObserver<Object> disposableObserver = getTermsConditonCLickObservable();
//        registerView.registerDialog.termsConditionButtonObservable().subscribe(disposableObserver);
//        compositeDisposable.add(disposableObserver);
//
//    }
//
//    private DisposableObserver<Object> getTermsConditonCLickObservable() {
//        return new DisposableObserver<Object>() {
//            @Override
//            public void onNext(Object o) {
//
//                registerView.showLoading(true);
//                termsConditionClicked();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Timber.e("Clicked");
//            }
//        };
//    }

    //regitering
    public void termsConditionClicked() {
        registerView.progressDialog.setMessage("Processing...");
        registerView.showLoading(true);
        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    registerView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    registerView.showMessage(termsContactPrivacyResponse.getMessage());

                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    registerView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                registerView.showLoading(false);
            }
        };


        registerModel.getTermsConditionObservable()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    public void privacyClicked() {
        registerView.progressDialog.setMessage("Processing...");
        registerView.showLoading(true);
        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    registerView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    registerView.showMessage(termsContactPrivacyResponse.getMessage());

                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    registerView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                registerView.showLoading(false);
            }
        };


        registerModel.getPrivacyPolicyObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    public void eulaClicked() {
        registerView.progressDialog.setMessage("Processing...");
        registerView.showLoading(true);
        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    registerView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    registerView.showMessage(termsContactPrivacyResponse.getMessage());

                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    registerView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    registerView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    registerView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    registerView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                registerView.showLoading(false);
            }
        };


        registerModel.getEulaObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
    ///////


}
