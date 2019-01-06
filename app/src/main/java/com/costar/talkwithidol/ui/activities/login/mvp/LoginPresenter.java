package com.costar.talkwithidol.ui.activities.login.mvp;

import com.costar.talkwithidol.app.network.EulaParams;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordParams;
import com.costar.talkwithidol.app.network.models.forgotPassword.ForgotPasswordResponse;
import com.costar.talkwithidol.app.network.models.login.LoginParams;
import com.costar.talkwithidol.app.network.models.login.LoginResponse.LoginResponse;
import com.costar.talkwithidol.app.network.models.registerFCM.FCMParams;
import com.costar.talkwithidol.app.network.models.registerFCM.RegisterFCMIDResponse;
import com.costar.talkwithidol.ext.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;
import static com.costar.talkwithidol.ui.activities.login.LoginActivity.sharedPreferences;

/**
 * Created by dell on 8/7/2017.
 */

public class LoginPresenter {

    private final LoginModel loginModel;
    private final LoginView loginView;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginPresenter(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;
    }


    public void onCreate() {
        registerButtonObservable();
        formValidation();
        loginUser();
        sendFCMRegID();
        sendUserAgreement();
        forgorPasswordClicked();

    }

    public void onDestroy() {

    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {
                    //validateEmailOnServer();

                    loginView.enableLoginButton(true);
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


        Flowable.combineLatest(loginView.emailAddressChangeObservable(),
                loginView.passwordChangeObservable(),

                (newEmail, newPassword) -> {

                    boolean isEmailValid = !isEmpty(newEmail);
                    if (!isEmailValid)
                        loginView.setEmailError("Invalid Phone Number");

                    boolean isPasswordValid = !isEmpty(newPassword);

                    if (!isPasswordValid)
                        loginView.setPasswordError("Password is Empty");

                    return isEmailValid && isPasswordValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void registerButtonObservable() {
        loginView.registerButtonObservable()
                .subscribe(o -> loginModel.startRegisterActivity());
    }


    //hit api and get response
    private void loginUser() {

        DisposableObserver<LoginResponse> disposableObserver = new DisposableObserver<LoginResponse>() {

            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse.getSuccess() && loginResponse.getData() != null) {

                        loginModel.saveData(Constants.COOKIE, loginResponse.getData().getSessionName() + "=" + loginResponse.getData().getSessid());
                        loginModel.saveData(Constants.TOKEN, loginResponse.getData().getToken());
                        loginModel.saveData(Constants.USERID, loginResponse.getData().getUid());
                        loginModel.saveData(Constants.USERIMAGE, loginResponse.getData().getAvatarurl());

                        loginModel.saveData(Constants.FIRSTNAME, loginResponse.getData().getFirstName());
                        loginModel.saveData(Constants.LASTNAME, loginResponse.getData().getLastName());
                        loginModel.saveData(Constants.EMAIL, loginResponse.getData().getMail());
                        loginModel.saveData(Constants.REFERENCE, loginResponse.getData().getReference());
                        loginModel.saveData(Constants.MOBILE, loginResponse.getData().getMobile());
                        loginModel.saveData(Constants.COUNTRY, loginResponse.getData().getCountry());
                        loginModel.saveData(Constants.AVATERIMAGE, loginResponse.getData().getAvatarurl());
                        loginModel.saveData(Constants.PROFILEIMAGESetting, loginResponse.getData().getProfile_url());
                        if (loginResponse.getData().getEula_acceptance().equals("accepted")){
                            loginModel.startDashboard();
                        }else{
                            getUserEula();

                        }



                } else {
                    loginView.showMessage(loginResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    loginView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    loginView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    loginView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    loginView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }
                loginUser();

            }

            @Override
            public void onComplete() {

            }
        };


        loginView.loginButtonObservable()
                .doOnNext(__ -> loginView.showProgressDialog(true))
                .map(__ -> loginParams())
                .observeOn(Schedulers.io())
                .switchMap(loginModel::getUserLoginObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> loginView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void forgorPasswordClicked() {

        loginView.progressDialog.setMessage("Processing!");

        DisposableObserver<ForgotPasswordResponse> disposableObserver = new DisposableObserver<ForgotPasswordResponse>() {

            @Override
            public void onNext(ForgotPasswordResponse forgotPasswordResponse) {
                if (forgotPasswordResponse.getSuccess() == true) {
                    loginView.showMessage(forgotPasswordResponse.getData().getMessage());
                    loginView.forgotPasswordDialog.showDialog(false);


                } else {
                    loginView.showMessage(forgotPasswordResponse.getDeny());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    loginView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    loginView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    loginView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    loginView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }
                loginUser();

            }

            @Override
            public void onComplete() {

            }
        };


        loginView.forgotPasswordDialog.sendButtonObservable()
                .doOnNext(__ -> loginView.showProgressDialog(true))
                .map(__ -> forgotPasswordParams())
                .observeOn(Schedulers.io())
                .switchMap(loginModel::forgotPasswordObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> loginView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void sendFCMRegID() {

        DisposableObserver<RegisterFCMIDResponse> disposableObserver = new DisposableObserver<RegisterFCMIDResponse>() {

            @Override
            public void onNext(RegisterFCMIDResponse registerFCMIDResponse) {
               /* newsView.setNews(exploreNewsResponse);*/

                if (registerFCMIDResponse.getSuccess() == true) {

                } else {
                    sendFCMRegID();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    loginView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //loginView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    //loginView.showMessage("Please check your network connection");

                } else {
                    //loginView.showMessage(e.getMessage());
                }

                sendFCMRegID();
            }

            @Override
            public void onComplete() {

            }
        };

        loginModel.getFCMRegisterObservable(sharedPreferences.getString("regId", ""), "android")
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //initialize the parameters
    private FCMParams fcmParams() {
        return FCMParams.builder()
                .token(sharedPreferences.getString("regId", null))
                .type("android")
                .build();
    }

    //initialize the parameters
    private LoginParams loginParams() {
        return LoginParams.builder()
                .username(loginView.emailAddress())
                .password(loginView.password())
                .build();
    }

    private ForgotPasswordParams forgotPasswordParams() {
        return ForgotPasswordParams.builder()
                .name(loginView.forgotPasswordDialog.getCode())
                .build();
    }

    private String cookie() {
        return "SESS348e0d664eef3a0bfa94749e10649eae=jW9lx0YA3r2RYQIQ46AcJLyellHr5QpwCkPRh8DEWSU";
    }


    //error message return
    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public void getUserEula( ) {

        DisposableObserver<EulaResponse> disposableObserver = new DisposableObserver<EulaResponse>() {

            @Override
            public void onNext(EulaResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    if (termsContactPrivacyResponse.getData().getAcceptance().equals("none")) {
                        loginView.eulaAcceptDialog.showDialog(true);
                        loginView.eulaAcceptDialog.setData(termsContactPrivacyResponse);
                    }else{
                        loginModel.clearData();
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    loginView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    loginView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    loginView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    loginView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
            }
        };


        loginModel.getEulaObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private void sendUserAgreement() {

        loginView.progressDialog.setMessage("Processing!");

        DisposableObserver<EulaResponse> disposableObserver = new DisposableObserver<EulaResponse>() {

            @Override
            public void onNext(EulaResponse forgotPasswordResponse) {
                if (forgotPasswordResponse.getSuccess()) {
                    loginView.eulaAcceptDialog.showDialog(false);
                    loginModel.startDashboard();

                } else {
                    loginView.showMessage(forgotPasswordResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    loginView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    loginView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    loginView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    loginView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };


        loginView.eulaAcceptDialog.acceptButtonObservable()
                .doOnNext(__ -> loginView.showProgressDialog(true))
                .map(__ -> eulaParams())
                .observeOn(Schedulers.io())
                .switchMap(loginModel::postEulaObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> loginView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private EulaParams eulaParams(){
        return EulaParams.builder().acceptance("accepted").build();
    }



}
