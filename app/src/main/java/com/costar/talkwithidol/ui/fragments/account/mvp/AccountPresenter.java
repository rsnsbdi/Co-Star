package com.costar.talkwithidol.ui.fragments.account.mvp;


import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordResponse;
import com.costar.talkwithidol.app.network.models.TermsPolicyContact.TermsContactPrivacyResponse;
import com.costar.talkwithidol.app.network.models.logoutResponse.LogoutResponse;
import com.costar.talkwithidol.ext.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class AccountPresenter {

    private final AccountView accountView;
    private final AccountModel accountModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    //private FitnessUtils fitnessUtils;


    public AccountPresenter(AccountView accountView, AccountModel accountModel) {
        this.accountView = accountView;
        this.accountModel = accountModel;

    }


    public void onCreateView() {
        onLogoutClicked();
        onContactClicked();
        onPrivacyPolicyClicked();
        onTermsConditionClicked();
        onEulaClicked();
        accountView.changePasswordDialog.getPasswordClicked(this::changePasswordClicked);
    }


//    private void formValidation() {
//        //call when it is subscribe
//        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
//            @Override
//            public void onNext(Boolean isFormValid) {
//                if (isFormValid) {
//                    accountView.changePasswordDialog.send.setEnabled(true);
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
//        Flowable.combineLatest(accountView.changePasswordDialog.oldpasswwordCharSequenceFlowable(),
//                accountView.changePasswordDialog.newpasswordCharSequenceFlowable(),
//                accountView.changePasswordDialog.confirmPasswordCharSequenceFlowable(),
//
//                (oldPassword, newPassword, confirmPassword) -> {
//
//                    boolean isCurrentPasswordValid = !isEmpty(oldPassword);
//                    if (!isCurrentPasswordValid)
//                        accountView.changePasswordDialog.setOldPasswordError("Password is Empty");
//
//                    boolean isNewPasswordValid = !isEmpty(newPassword);
//                    if (!isNewPasswordValid)
//                        accountView.changePasswordDialog.setNewPasswordError("Password is Empty");
//
//                    boolean isConfirmPasswordValid = !isEmpty(confirmPassword);
//                    if (!isConfirmPasswordValid)
//                        accountView.changePasswordDialog.setConfirmPasswordError("Password is Empty");
//
//                    boolean isNewPasswordPolicyValid = !isEmpty(newPassword) && newPassword.length() >= 8 && newPassword.toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})");
//                    if (!isNewPasswordPolicyValid)
//                        accountView.changePasswordDialog.setNewPasswordError(("Password must contains atleast 8 character and must contain one capital letter and one small letter and number "));
//
//
//                    boolean isConfirmPasswordPolicyValid = !isEmpty(confirmPassword) && confirmPassword.length() >= 8 && confirmPassword.toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})");
//                    if (!isConfirmPasswordPolicyValid)
//                        accountView.changePasswordDialog.setConfirmPasswordError(("Password must contains atleast 8 character and must contain one capital letter and one small letter and number "));
//                    boolean isPasswordMatch = !isEmpty(confirmPassword) && newPassword.equals(confirmPassword);
//                    if (!isPasswordMatch) {
//                        accountView.changePasswordDialog.setConfirmPasswordError("Password donot match");
//                    }
//                    return isCurrentPasswordValid && isNewPasswordValid && isConfirmPasswordValid && isNewPasswordPolicyValid && isConfirmPasswordPolicyValid;
//
//
//                }).subscribe(disposableObserver);
//        compositeDisposable.add(disposableObserver);
//    }


    public void onDestroyView() {
        compositeDisposable.clear();
    }

    private void onLogoutClicked() {
        DisposableObserver<Object> disposableObserver = getLogoutCLickObservable();
        accountView.logoutButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Object> getLogoutCLickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                accountView.showProgressDialog(true);
                accountView.progressDialog.setMessage("Logging out...");
                logoutClicked();
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
    //regitering
    public void logoutClicked() {

        DisposableObserver<LogoutResponse> disposableObserver = new DisposableObserver<LogoutResponse>() {

            @Override
            public void onNext(LogoutResponse logoutResponse) {

                if (logoutResponse.getSuccess()) {
                    accountView.redirectToLogin();
                } else {
                    accountView.showMessage(logoutResponse.getMessage());
                  // accountView.redirectToLogin();
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");
                    onLogoutClicked();

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    accountView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                accountView.showProgressDialog(false);
            }
        };


        accountModel.getLogoutObservable()
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> ev
                // entView.showLoading(false))
                .subscribe(disposableObserver);
    }


    //terms and condition
    private void onTermsConditionClicked() {
        DisposableObserver<Object> disposableObserver = getTermsConditonCLickObservable();
        accountView.termsConditionButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Object> getTermsConditonCLickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                accountView.showProgressDialog(true);
                accountView.progressDialog.setMessage("Processing...");
                termsConditionClicked();
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
    //regitering
    public void termsConditionClicked() {

        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    accountView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    accountView.showMessage(termsContactPrivacyResponse.getMessage());

                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    accountView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                accountView.showProgressDialog(false);
            }
        };


        accountModel.getTermsConditionObservable()
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> ev
                // entView.showLoading(false))
                .subscribe(disposableObserver);
    }
    ///////



    //privacy and policy
    private void onPrivacyPolicyClicked() {
        DisposableObserver<Object> disposableObserver = getPrivacyPolicyCLickObservable();
        accountView.privacyPolicyButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Object> getPrivacyPolicyCLickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                accountView.showProgressDialog(true);
                accountView.progressDialog.setMessage("Processing...");
                privacyPolicyClicked();
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

    public void privacyPolicyClicked() {

        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {



                    accountView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    accountView.showMessage(termsContactPrivacyResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    accountView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                accountView.showProgressDialog(false);
            }
        };


        accountModel.getPrivacyPolicyObservable()
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> ev
                // entView.showLoading(false))
                .subscribe(disposableObserver);
    }
    ///////


    //contact
    private void onContactClicked() {
        DisposableObserver<Object> disposableObserver = getContactCLickObservable();
        accountView.contactDetailButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Object> getContactCLickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                accountView.showProgressDialog(true);
                accountView.progressDialog.setMessage("Processing...");
                contactClicked();
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
    //regitering
    public void contactClicked() {

        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    accountView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    accountView.showMessage(termsContactPrivacyResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    accountView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                accountView.showProgressDialog(false);
            }
        };


        accountModel.getContactObservable()
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> ev
                // entView.showLoading(false))
                .subscribe(disposableObserver);
    }
    ///////



    //privacy and policy
    private void onEulaClicked() {
        DisposableObserver<Object> disposableObserver = getEulaCLickObservable();
        accountView.eulaButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Object> getEulaCLickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                accountView.showProgressDialog(true);
                accountView.progressDialog.setMessage("Processing...");
                eulaClicked();
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

    public void eulaClicked() {

        DisposableObserver<TermsContactPrivacyResponse> disposableObserver = new DisposableObserver<TermsContactPrivacyResponse>() {

            @Override
            public void onNext(TermsContactPrivacyResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    accountView.termsPrivacyContact.setData(termsContactPrivacyResponse);
                } else {
                    accountView.showMessage(termsContactPrivacyResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    accountView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                accountView.showProgressDialog(false);
            }
        };


        accountModel.getEulaObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private void changePasswordClicked() {
        accountView.progressDialog.show();
        accountView.progressDialog.setMessage("Updating your password");
        accountView.progressDialog.setCanceledOnTouchOutside(false);

        DisposableObserver<ChangePasswordResponse> disposableObserver = new DisposableObserver<ChangePasswordResponse>() {

            @Override
            public void onNext(ChangePasswordResponse changePasswordResponse) {
                if (changePasswordResponse.getSuccess()) {
                    accountView.changePasswordDialog.showDialog(false);
                    accountView.progressDialog.dismiss();
                    accountView.showMessage("Your password has been successfully updated!");
                    accountModel.saveData(Constants.COOKIE, changePasswordResponse.getData().getSessionName() + "=" + changePasswordResponse.getData().getSessid());
                    accountModel.saveData(Constants.TOKEN, changePasswordResponse.getData().getToken());
                    accountModel.saveData(Constants.USERID, changePasswordResponse.getData().getUid());
                    accountModel.saveData(Constants.FIRSTNAME, changePasswordResponse.getData().getFirstName());
                    accountModel.saveData(Constants.LASTNAME, changePasswordResponse.getData().getLastName());
                    accountModel.saveData(Constants.EMAIL, changePasswordResponse.getData().getMail());
                    accountModel.saveData(Constants.REFERENCE, changePasswordResponse.getData().getReference());
                    accountModel.saveData(Constants.MOBILE, changePasswordResponse.getData().getMobile());
                    accountModel.saveData(Constants.COUNTRY, changePasswordResponse.getData().getCountry());
                } else {
                    accountView.showMessage(changePasswordResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    accountView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    accountView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    accountView.showMessage("Please check your network connection");

                } else {
                    accountView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                accountView.progressDialog.dismiss();
            }
        };

        accountModel.changePasswordObservable(accountView.changePasswordDialog.changePasswordParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
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
