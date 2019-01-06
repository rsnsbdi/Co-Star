package com.costar.talkwithidol.ui.activities.newsdetail.mvp;


import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.newsDetail.NewsDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;
import com.google.gson.JsonArray;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;

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

public class NewsDtailPresenter {
    public static String channelId;
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    private final NewsDtailView newsDtailView;
    private final NewsDtailModel newsDtailModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;


    public NewsDtailPresenter(NewsDtailView newsDtailView, NewsDtailModel newsDtailModel, PreferencesManager preferencesManager) {
        this.newsDtailView = newsDtailView;
        this.newsDtailModel = newsDtailModel;
        this.preferencesManager = preferencesManager;

    }


    public void onCreate() {
        getNewsDetail();
        freeTrialClicked();
        subscribeClicked();
        payClicked();
        subscriptionStepTwo();
        formValidation();
        LikeClicked();

    }


    private void LikeClicked() {


        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess() && likeEntityReponse.getData() != null) {
                    newsDtailView.setLike(likeEntityReponse);

                } else {
                    newsDtailView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");
                    LikeClicked();

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    newsDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };

        newsDtailView.likeClickObservable()
                .map(__ -> likeEntityParams())
                .observeOn(Schedulers.io())
                .switchMap(newsDtailModel::getLikeEntittyObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(NewsDtailActivity.videoId).type("node")
                .build();
    }

    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    newsDtailView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(newsDtailView.creditCardDialog.cardnameCharSequenceFlowable(),
                newsDtailView.creditCardDialog.cardnoCharSequenceFlowable(),
                newsDtailView.creditCardDialog.monthCharSequenceFlowable(),
                newsDtailView.creditCardDialog.yearCharSequenceFlowable(),
                newsDtailView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(newsDtailView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        newsDtailView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(newsDtailView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        newsDtailView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(newsDtailView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        newsDtailView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(newsDtailView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        newsDtailView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(newsDtailView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        newsDtailView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(newsDtailView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        newsDtailView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    // get explore news list
    private void getNewsDetail() {

        newsDtailView.showLoadingDialog(true);

        DisposableObserver<NewsDetailResponse> disposableObserver = new DisposableObserver<NewsDetailResponse>() {

            @Override
            public void onNext(NewsDetailResponse newsDetailResponse) {

                if (newsDetailResponse.getSuccess() && newsDetailResponse.getData() != null) {
                    newsDtailView.setNewsDetail(newsDetailResponse);
                    if (newsDetailResponse.getData().getAccess().equals("denied")) {
                        channelId = newsDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                        subscribedgateway = newsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                        if (subscribedgateway.equals("apple")) {
                            isApple = true;
                        }
                        subscribedmessage = newsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                    }
                } else {
                    if (newsDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        newsDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(newsDtailView.activity);
                    }
                    newsDtailView.showMessage(newsDetailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                    getNewsDetail();

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                newsDtailView.showLoadingDialog(false);
            }
        };

        newsDtailModel.getNewsDetailObservable(NewsDtailActivity.videoId, newsDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void freeTrialClicked() {


        DisposableObserver<FreeTrialResponse> disposableObserver = new DisposableObserver<FreeTrialResponse>() {

            @Override
            public void onNext(FreeTrialResponse freeTrialResponse) {
                if (freeTrialResponse.getSuccess() && freeTrialResponse.getData() != null) {
                    newsDtailView.showMessage(freeTrialResponse.getData().getMessage());
                    newsDtailView.deniedDialog.showDialog(false);

                } else {
                    newsDtailView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        newsDtailView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ -> newsDtailView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(newsDtailModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> newsDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {

        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess() && subscriptionStepOneResponse.getData() != null) {


                    if (isApple) {
                        newsDtailView.webSubsDialog.showDialog(true);
                        newsDtailView.webSubsDialog.setData(subscribedmessage);

                    } else {
                        newsDtailView.confirmDialog.showDialog(true);
                        newsDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        newsDtailView.confirmDialog.setData(subscriptionStepOneResponse);
                    }
                } else {
                    newsDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        newsDtailView.deniedDialog.subscribeNowButtonObservable()
                .doOnNext(__ -> newsDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(newsDtailModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> newsDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {

        newsDtailView.showProgressDialog(true);

        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess() && paydockConfigurationResponse.getData() != null) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());

                    newsDtailView.creditCardDialog.showDialog(true);
                } else {
                    newsDtailView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                newsDtailView.showProgressDialog(false);
            }
        };
        newsDtailModel.getPaydockConfigurationObservable()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private void payClicked() {


        DisposableObserver<PaydockCustomerResponse> disposableObserver = new DisposableObserver<PaydockCustomerResponse>() {

            @Override
            public void onNext(PaydockCustomerResponse paydockCustomerResponse) {
                if (paydockCustomerResponse.getStatus() == 201) {
                    preferencesManager.save(Constants.CUSTOMER_ID, paydockCustomerResponse.getResource().getData().getId());
                    newSubscription();

                } else {
                    newsDtailView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    newsDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();
            }

            @Override
            public void onComplete() {

            }
        };
        newsDtailView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> newsDtailView.showProgressDialog(true))
                .map(__ -> newsDtailView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(newsDtailModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> newsDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {

        newsDtailView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess() && subscriptionStepOneResponse.getData() != null) {
//                    newsDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
//                    newsDtailView.confirmDialog.showDialog(true);
//                    newsDtailView.confirmDialog.setData(subscriptionStepOneResponse);
                    newsDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    newsDtailView.confirmDialog.showDialog(false);
                    newsDtailView.creditCardDialog.showDialog(false);
                    newsDtailView.deniedDialog.showDialog(false);
                    newsDtailView.isDialog = false;
                } else {
                    newsDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                newsDtailView.showProgressDialog(false);
            }
        };
        newsDtailModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess() && subscriptionStepTwoResponse.getData() != null) {
//                    newsDtailView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
//                    newsDtailView.confirmDialog.showDialog(false);
//                    newsDtailView.creditCardDialog.showDialog(false);
//                    newsDtailView.deniedDialog.showDialog(false);
//                    newsDtailView.isDialog = false;
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        newsDtailView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        newsDtailView.confirmDialog.showDialog(false);
                        newsDtailView.deniedDialog.showDialog(false);
                        newsDtailView.isDialog = false;
                    }
                } else {
                    newsDtailView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsDtailView.showMessage("Please check your network connection");

                } else {
                    newsDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        newsDtailView.confirmButtonObservable()
                .doOnNext(__ -> newsDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(newsDtailModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> newsDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    public FreeTrialParams freeTrialParams() {
        return FreeTrialParams.builder().id(channelId).build();
    }

    public JsonArray channelsSubscribedArray() {
        JsonArray channels_list = new JsonArray();
        channels_list.add(channelId);
        return channels_list;
    }


    public JsonArray channelsUnsubscribedArray() {
        JsonArray channels_list = new JsonArray();

        return channels_list;
    }


    ChannelSubsParams channelSubsParams() {
        return ChannelSubsParams.builder().subscribed_channels(channelsSubscribedArray()).unsubscribed_channels(channelsUnsubscribedArray()).customer_id("").build();
    }

    ChannelSubsParams channelSubsParams1() {
        return ChannelSubsParams.builder().subscribed_channels(channelsSubscribedArray()).unsubscribed_channels(channelsUnsubscribedArray()).customer_id(preferencesManager.get(Constants.CUSTOMER_ID)).build();
    }


    public void onDestroyView() {
        compositeDisposable.clear();
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
