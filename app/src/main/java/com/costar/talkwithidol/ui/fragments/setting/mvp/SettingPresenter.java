package com.costar.talkwithidol.ui.fragments.setting.mvp;


import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.RxEvent;
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

public class SettingPresenter {

    private final SettingView settingView;
    private final SettingModel settingModel;
    public PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;


    public SettingPresenter(SettingView settingView, SettingModel settingModel, PreferencesManager preferencesManager) {
        this.settingView = settingView;
        this.settingModel = settingModel;
        this.preferencesManager = preferencesManager;

    }


    public void onCreateView() {
        getUserDetails();

        RxBus.getInstance().toObservable().subscribe(o ->{
            if (o instanceof RxEvent.LoginApiResponse) {
                RxEvent.LoginApiResponse loginResponse = (RxEvent.LoginApiResponse) o;
            if (loginResponse.loginResponse.getData() != null){
                settingView.full_name.setText(loginResponse.loginResponse.getData().getFirstName()+" "+loginResponse.loginResponse.getData().getLastName());
            }
            else{
                settingView.full_name.setText("");
            }
        }});
    }




    private void getUserDetails() {

        DisposableObserver<SettingsProfileResponse> disposableObserver = new DisposableObserver<SettingsProfileResponse>() {

            @Override
            public void onNext(SettingsProfileResponse settingsProfileResponse) {
                if (settingsProfileResponse.getSuccess() && settingsProfileResponse.getData() != null) {
                    settingView.setProfile(settingsProfileResponse);
                } else {
                    preferencesManager.clear();
                    settingModel.startLogin();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    settingView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // settingView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    settingView.showMessage("Please check your network connection");

                } else {
                    settingView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        settingModel.getProfileObservable(settingModel.getData(Constants.USERID), settingModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }






    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public void onDestroyView() {
        compositeDisposable.clear();
    }


}
