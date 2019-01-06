package com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp;


import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.channeldetals.ChannelDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;
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

public class ChannelsPresenter {
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    private final ChannelsView channelsView;
    private final ChannelsModel channelsModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    //private FitnessUtils fitnessUtils;


    public ChannelsPresenter(ChannelsView channelsView, ChannelsModel channelsModel, PreferencesManager preferencesManager) {
        this.channelsView = channelsView;
        this.channelsModel = channelsModel;
        this.preferencesManager = preferencesManager;
    }


    public void onCreateView() {

        getChannelDetal();
        getCLickForSubscribe();
        payClicked();
        subscriptionStepTwo();
        formValidation();
    }

    public  String getPreference(){
        return channelsModel.getData(Constants.CurrentFragment);
    }

    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    channelsView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(channelsView.creditCardDialog.cardnameCharSequenceFlowable(),
                channelsView.creditCardDialog.cardnoCharSequenceFlowable(),
                channelsView.creditCardDialog.monthCharSequenceFlowable(),
                channelsView.creditCardDialog.yearCharSequenceFlowable(),
                channelsView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(channelsView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        channelsView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(channelsView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        channelsView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(channelsView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        channelsView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(channelsView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        channelsView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(channelsView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        channelsView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(channelsView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        channelsView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getChannelDetal() {
//        channelsView.showLoading(true);
        DisposableObserver<ChannelDetailResponse> disposableObserver = new DisposableObserver<ChannelDetailResponse>() {

            @Override
            public void onNext(ChannelDetailResponse channelDetailResponse) {
                if (channelDetailResponse.getSuccess() == true && channelDetailResponse.getData() != null) {
                    if (channelDetailResponse.getData() != null)
                        subscribedgateway = channelDetailResponse.getData().getSubscribed_gateway();
                    if (subscribedgateway.equals("apple")) {
                        isApple = true;
                    }
                    subscribedmessage = channelDetailResponse.getData().getSubscribed_message();
                    channelsView.setChannelDetail(channelDetailResponse);
                } else {

                    if (channelDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(channelsView.activity);
                    }
                    channelsView.showMessage(channelDetailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    getChannelDetal();
//                    channelsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");

                } else {
                    channelsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

//                channelsView.showLoading(false);
            }
        };

        channelsModel.getChannelDetailObservable(ChannelDetailFragment.videoId, channelsModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getCLickForSubscribe() {
        DisposableObserver<Object> disposableObserver = getSubscribeClickObservable();
        channelsView.subscribeButtonObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Object> getSubscribeClickObservable() {
        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object object) {
                if (isApple) {
                    channelsView.webSubsDialog.showDialog(true);
                    channelsView.webSubsDialog.setData(subscribedmessage);
                } else {
                    subscribeClicked();
                }

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


    private void subscribeClicked() {
        channelsView.showProgressDialog(true);

        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess() && subscriptionStepOneResponse.getData() != null) {
                    channelsView.showMessage(subscriptionStepOneResponse.getData().getMessage());
//                    if (subscriptionStepOneResponse.getData().getSubscription().equals("payment_required")) {
//                        getPaydockConfig();
//
//                    } else if (subscriptionStepOneResponse.getData().getSubscription().equals("confirmation_required")) {
                    channelsView.confirmDialog.showDialog(true);
                    channelsView.confirmDialog.setData(subscriptionStepOneResponse);
//                    }
                } else {
                    channelsView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelsView.showMessage("Time Out");
                    subscribeClicked();

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");

                } else {
                    channelsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                channelsView.showProgressDialog(false);
            }
        };

        channelsModel.subscriptionStepOne(channelSubsParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {

        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess() && paydockConfigurationResponse.getData() != null) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    channelsView.creditCardDialog.showDialog(true);
                } else {
                    channelsView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");

                } else {
                    channelsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        channelsModel.getPaydockConfigurationObservable()
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
                    channelsView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    //channelsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelsView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();

            }

            @Override
            public void onComplete() {

            }
        };
        channelsView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> channelsView.showProgressDialog(true))
                .map(__ -> channelsView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(channelsModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> channelsView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {
        channelsView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    channelsView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    channelsView.confirmDialog.showDialog(false);
                    channelsView.creditCardDialog.showDialog(false);
                    getChannelDetal();
                } else {
                    channelsView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");

                } else {
                    channelsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelsView.showProgressDialog(false);
            }
        };
        channelsModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess()) {
                    channelsView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        channelsView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        channelsView.confirmDialog.showDialog(false);
                        getChannelDetal();
                    }
                } else {
                    channelsView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    channelsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelsView.showMessage("Please check your network connection");

                } else {
                    channelsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        channelsView.confirmButtonObservable()
                .doOnNext(__ -> channelsView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(channelsModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> channelsView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    public FreeTrialParams freeTrialParams() {
        return FreeTrialParams.builder().id(ChannelDetailFragment.videoId).build();
    }

    public JsonArray channelsSubscribedArray() {
        JsonArray channels_list = new JsonArray();
        channels_list.add(ChannelDetailFragment.videoId);
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
