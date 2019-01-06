package com.costar.talkwithidol.ui.activities.eulaafterregister.mvp;

import com.costar.talkwithidol.app.network.EulaParams;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by shreedhar on 12/17/17.
 */

public class EulaPresenter {

    private final EulaView eulaView;
    private final EulaModel eulaModel;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EulaPresenter(EulaView eulaView, EulaModel eulaModel) {
        this.eulaView = eulaView;
        this.eulaModel = eulaModel;
    }

    public void onCreate() {
        getUserEula();
        sendUserAgreement();

    }

    public void getUserEula() {
        eulaView.showLoading(true);
        DisposableObserver<EulaResponse> disposableObserver = new DisposableObserver<EulaResponse>() {

            @Override
            public void onNext(EulaResponse termsContactPrivacyResponse) {

                if (termsContactPrivacyResponse.getSuccess()) {
                    if (termsContactPrivacyResponse.getData().getAcceptance().equals("none")) {
                        eulaView.setData(termsContactPrivacyResponse);
                    } else {
                        eulaView.showMessage(termsContactPrivacyResponse.getMessage());
                    }

                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eulaView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eulaView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eulaView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eulaView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }

            }

            @Override
            public void onComplete() {
                eulaView.showLoading(false);
            }
        };


        eulaModel.getEulaObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private void sendUserAgreement() {

        DisposableObserver<EulaResponse> disposableObserver = new DisposableObserver<EulaResponse>() {

            @Override
            public void onNext(EulaResponse forgotPasswordResponse) {
                if (forgotPasswordResponse.getSuccess()) {
                    eulaModel.startHome();

                } else {
                    eulaView.showMessage(forgotPasswordResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eulaView.showMessage(getErrorMessage(responseBody));
                    sendUserAgreement();
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eulaView.showMessage("Time Out");
                    sendUserAgreement();

                } else if (e instanceof IOException) {
                    eulaView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();
                    sendUserAgreement();

                } else {
                    //todo changes
                    eulaView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                    sendUserAgreement();

                }

            }

            @Override
            public void onComplete() {

            }
        };

        eulaView.acceptButtonObservable()
                .doOnNext(__ -> eulaView.showDialog(true))
                .map(__ -> eulaParams())
                .observeOn(Schedulers.io())
                .switchMap(eulaModel::postEulaObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eulaView.showDialog(false))
                .subscribe(disposableObserver);
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private EulaParams eulaParams() {
        return EulaParams.builder().acceptance("accepted").build();
    }

}
