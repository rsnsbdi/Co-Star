package com.costar.talkwithidol.ui.fragments.preference.mvp;


import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceParams;
import com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse.UpdatePreferenceResponse;
import com.costar.talkwithidol.app.network.models.UserPreferences.UserPreferencesResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.google.gson.JsonObject;
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

public class PreferencePresenter {

    private final PreferenceView preferenceView;
    private final PreferenceModel preferenceModel;
    //private FitnessUtils fitnessUtils;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;


    public PreferencePresenter(PreferenceView preferenceView, PreferenceModel preferenceModel, PreferencesManager preferencesManager) {
        this.preferenceView = preferenceView;
        this.preferenceModel = preferenceModel;
        this.preferencesManager = preferencesManager;
    }


    public void onCreateView() {
        getUserPreferences();
        doneClicked();

    }

    private void getUserPreferences() {

        DisposableObserver<UserPreferencesResponse> disposableObserver = new DisposableObserver<UserPreferencesResponse>() {

            @Override
            public void onNext(UserPreferencesResponse userPreferencesResponse) {

                if (userPreferencesResponse.getSuccess()&& userPreferencesResponse.getData() != null) {
                    preferenceView.setUserPreference(userPreferencesResponse);
                }else{
                    preferenceView.showMessage(userPreferencesResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    preferenceView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // preferenceView.showMessage("Time Out");
                    getUserPreferences();

                } else if (e instanceof IOException) {
                    preferenceView.showMessage("Please check your network connection");

                } else {
                    preferenceView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        preferenceModel.getUserPreferences()
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))s
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void doneClicked() {

        DisposableObserver<UpdatePreferenceResponse> disposableObserver = new DisposableObserver<UpdatePreferenceResponse>() {

            @Override
            public void onNext(UpdatePreferenceResponse updatePreferenceResponse) {
                if (updatePreferenceResponse.getSuccess()) {
                    preferenceView.showMessage("Preference has been updated");

                } else {
                    preferenceView.showMessage("Server Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    preferenceView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                   // preferenceView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    preferenceView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    preferenceView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        preferenceView.doneButtonObservable()
                .doOnNext(__ -> preferenceView.showLoadingDialog(true))
                .map(__ -> updatePreferenceParams())
                .observeOn(Schedulers.io())
                .switchMap(preferenceModel::updatePreferenceResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> preferenceView.showLoadingDialog(false))
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

    public void onDestroyView() {
        compositeDisposable.clear();
    }


    private UpdatePreferenceParams updatePreferenceParams() {
        return UpdatePreferenceParams.builder().email_notification(emailNotification()).event_notification(eventNotification()).build();
    }


    private JsonObject emailNotification() {
        JsonObject emailNotification = new JsonObject();
        emailNotification.addProperty("special_offers", preferencesManager.getBoolean(Constants.SWITCH1));
        emailNotification.addProperty("whats_on", preferencesManager.getBoolean(Constants.SWITCH2));
        return emailNotification;

    }

    private JsonObject eventNotification() {
        JsonObject eventNotification = new JsonObject();
        eventNotification.addProperty("new_events", preferencesManager.getBoolean(Constants.SWITCH3));
        eventNotification.addProperty("closing_events", preferencesManager.getBoolean(Constants.SWITCH4));
        return eventNotification;

    }


}
