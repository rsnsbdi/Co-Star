package com.costar.talkwithidol.ui.activities.homepage.mvp;

import android.app.Activity;
import android.view.View;

import com.costar.talkwithidol.app.network.models.NotificationCountResponse.NotificationCountResponse;
import com.costar.talkwithidol.app.network.models.NtificationCountParam;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.costar.talkwithidol.ui.activities.homepage.HomePageActivity.backPressed;

/**
 * Created by dell on 8/8/2017.
 */

public class HomePagePresenter {

    private final HomePageView homePageView;
    private final HomePageModel homePageModel;
    PreferencesManager preferencesManager;


    boolean logout = true;
    private String count = "0";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public HomePagePresenter(HomePageView homePageView, HomePageModel homePageModel, PreferencesManager preferencesManager) {
        this.homePageView = homePageView;
        this.homePageModel = homePageModel;
        this.preferencesManager = preferencesManager;
    }

    public void onCreate() {

        getNotificationCount(homePageView.activity);

        RxBus.getInstance().toObservable().subscribe(o -> {
            if (o instanceof String) {
                homePageView.switchFrg(o.toString());
            } else if (o instanceof Integer) {
                if ((Integer) o == 1) {
                    if(backPressed==false){
                        backPressed= true;
                        homePageView.onBackPressed();
                    }

                } else {
                    homePageView.changeIndicator();

                }
            }
        });


//        homePageView.addModes("0");

        // homePageView.setToolbarTitle();
    }


    // get explore event list
    private void getNotificationCount(Activity activity) {

        DisposableObserver<NotificationCountResponse> disposableObserver = new DisposableObserver<NotificationCountResponse>() {

            @Override
            public void onNext(NotificationCountResponse notificationCountResponse) {
                if (notificationCountResponse.getSuccess() && notificationCountResponse.getData() != null) {
                    if (!count.equals(notificationCountResponse.getData().getTotal())) {
//                        homePageView.addModes(notificationCountResponse.getData().getTotal());
                        count = notificationCountResponse.getData().getTotal();
                        if (!count.equals("0")) {
                            homePageView.notificationCount.setText(count);
                            homePageView.notificationCount.setVisibility(View.VISIBLE);
                        } else {
                            homePageView.notificationCount.setVisibility(View.GONE);
                        }
                    }

                } else {
                    try {
                        if (logout) {
                            if (notificationCountResponse.getMessage().contains(Constants.AccessDenied)) {
                                logout = false;
                                HomePageActivity.startLogout(activity);
                                preferencesManager.clear();

                            }
                        }

                        // homePageView.showMessage(notificationCountResponse.getMessage());
                    } catch (Exception e) {

                    }


                }

                getNotificationCount(homePageView.activity);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    // homePageView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homePageView.showMessage("Time Out");
                    getNotificationCount(activity);

                } else if (e instanceof IOException) {
                    // homePageView.showMessage("Please check your network connection");

                } else {
                    //homePageView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homePageModel.getNotificationContObservable(notificationparams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //initialize the parameters
    private NtificationCountParam notificationparams() {
        return NtificationCountParam.builder()
                .state("unseen")
                .build();
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


}
