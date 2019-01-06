package com.costar.talkwithidol.ui.fragments.channel.mvp;


import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ChannelSubscriptionResponse;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.ExpiringChannel;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.SubscribedChannel;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.SubscriptionInfo;
import com.costar.talkwithidol.app.network.models.UserChannelSubscription.UnsubscribedChannel;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;
import com.google.gson.JsonArray;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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

public class ChannelPresenter {

    public static String key = "";
    private final ChannelView channelView;
    private final ChannelModel channelModel;
    //private FitnessUtils fitnessUtils;
    private PreferencesManager preferencesManager;
    private ArrayList<Integer> subscribedChannelsArrayList = new ArrayList<>();
    private ArrayList<Integer> unsubscribedChannelArrayList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    private SubscriptionInfo subscriptionInfo;


    public ChannelPresenter(ChannelView channelView, ChannelModel channelModel, PreferencesManager preferencesManager) {
        this.channelView = channelView;
        this.channelModel = channelModel;
        this.preferencesManager = preferencesManager;

    }


    public void onCreateView() {
        getSubscriptionList();
        subscribedtoggleClickObservable();
        unsubscribedtoggleClickObservable();
        expiringtoggleClickObservable();
        payClicked();
        subscriptionStepTwo();
        formValidation();


    }


    private void subscribedtoggleClickObservable() {
        DisposableObserver<SubscribedChannel> disposableObserver = getSubscribedClickObservable();
        channelView.getSubscribedToggleClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<SubscribedChannel> getSubscribedClickObservable() {
        return new DisposableObserver<SubscribedChannel>() {
            @Override
            public void onNext(SubscribedChannel datum) {
                if (!subscriptionInfo.getSubscribed_gateway().equals("apple")) {

                    subscribedChannelsArrayList.clear();
                    unsubscribedChannelArrayList.clear();
                    unsubscribedChannelArrayList.add(Integer.parseInt(datum.getChannelId()));
                    updateSubscription();
                } else {
                    channelView.iosSubscriberDialog.showDialog(true);
                    channelView.iosSubscriberDialog.setChannelData(subscriptionInfo);
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

    private void unsubscribedtoggleClickObservable() {
        DisposableObserver<UnsubscribedChannel> disposableObserver = getunSubscribedClickObservable();
        channelView.getUnSubscribedToggleClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<UnsubscribedChannel> getunSubscribedClickObservable() {
        return new DisposableObserver<UnsubscribedChannel>() {
            @Override
            public void onNext(UnsubscribedChannel datum) {
                if (!subscriptionInfo.getSubscribed_gateway().equals("apple")) {
                    subscribedChannelsArrayList.clear();
                    unsubscribedChannelArrayList.clear();
                    subscribedChannelsArrayList.add(Integer.parseInt(datum.getChannelId()));
                    updateSubscription();
                } else {
                    channelView.iosSubscriberDialog.showDialog(true);
                    channelView.iosSubscriberDialog.setChannelData(subscriptionInfo);
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

    private void expiringtoggleClickObservable() {
        DisposableObserver<ExpiringChannel> disposableObserver = getExpiringClickObservable();
        channelView.getExpiringToggleClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<ExpiringChannel> getExpiringClickObservable() {
        return new DisposableObserver<ExpiringChannel>() {
            @Override
            public void onNext(ExpiringChannel datum) {
                if (!subscriptionInfo.getSubscribed_gateway().equals("apple")) {
                    subscribedChannelsArrayList.clear();
                    unsubscribedChannelArrayList.clear();
                    subscribedChannelsArrayList.add(Integer.parseInt(datum.getChannelId()));
                    updateSubscription();
                } else {
                    channelView.iosSubscriberDialog.showDialog(true);
                    channelView.iosSubscriberDialog.setChannelData(subscriptionInfo);
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

    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    channelView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(channelView.creditCardDialog.cardnameCharSequenceFlowable(),
                channelView.creditCardDialog.cardnoCharSequenceFlowable(),
                channelView.creditCardDialog.monthCharSequenceFlowable(),
                channelView.creditCardDialog.yearCharSequenceFlowable(),
                channelView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(channelView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        channelView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(channelView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        channelView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(channelView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        channelView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(channelView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        channelView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(channelView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        channelView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(channelView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        channelView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getSubscriptionList() {
        channelView.showLoading(true);
        DisposableObserver<ChannelSubscriptionResponse> disposableObserver = new DisposableObserver<ChannelSubscriptionResponse>() {

            @Override
            public void onNext(ChannelSubscriptionResponse channelSubscriptionResponse) {
                if (channelSubscriptionResponse.getSuccess() && channelSubscriptionResponse.getData() != null) {
                    subscriptionInfo = channelSubscriptionResponse.getData().getSubscriptionInfo();
                    channelView.setChannels(channelSubscriptionResponse);

                } else {
                   /* if (!channelSubscriptionResponse.getEmpty())
                        channelView.showMessage(channelSubscriptionResponse.getMessage());*/
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelView.showMessage("Time Out");
                    getSubscriptionList();

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");

                } else {
                    channelView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelView.showLoading(false);
            }
        };

        channelModel.getSubscriptionList(preferencesManager.get(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void updateSubscription() {
        channelView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    channelView.confirmDialog.showDialog(true);
                    channelView.confirmDialog.setData(subscriptionStepOneResponse);
                } else {
                    channelView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");

                } else {
                    channelView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelView.showProgressDialog(false);
            }
        };

        channelModel.subscriptionStepOne(channelSubsParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getPaydockConfig() {
        channelView.showProgressDialog(true);
        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess()) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    key = preferencesManager.get(Constants.PAYDOCK_SECRET_KEY);
                    channelView.creditCardDialog.showDialog(true);
                } else {
                    channelView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //channelView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");

                } else {
                    channelView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelView.showProgressDialog(false);

            }
        };
        channelModel.getPaydockConfigurationObservable()
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
                    channelView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    channelView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();
            }

            @Override
            public void onComplete() {

            }
        };
        channelView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> channelView.showProgressDialog(true))
                .map(__ -> channelView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(channelModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> channelView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {
        channelView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    channelView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    channelView.confirmDialog.showDialog(false);
                    channelView.creditCardDialog.showDialog(false);
                    getSubscriptionList();
                    subscribedChannelsArrayList.clear();
                    unsubscribedChannelArrayList.clear();
                } else {
                    channelView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    channelView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");

                } else {
                    channelView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelView.showProgressDialog(false);
            }
        };
        channelModel.subscriptionStepTwo(channelSubsParams1())
                // .doOnNext(__ ->eventDtailView.showLoading(true))
//                .map(__ -> channelSubsParams1())
                .subscribeOn(Schedulers.io())
//                .switchMap(communityDtailModel::newSubscription)
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnEach(__ -> registerView.showLoading(false))
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {


        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess()) {

                    channelView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        channelView.confirmDialog.showDialog(false);
                        channelView.creditCardDialog.showDialog(false);
                        getSubscriptionList();
                        subscribedChannelsArrayList.clear();
                        unsubscribedChannelArrayList.clear();
                    }
                } else {
                    channelView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    channelView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelView.showMessage("Please check your network connection");

                } else {
                    channelView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        channelView.confirmDialog.confirmButtonObservable()
                .doOnNext(__ -> channelView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(channelModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> channelView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    public ChannelSubsParams channelSubsParams() {
        return ChannelSubsParams.builder().subscribed_channels(channelsSubscribedArray())
                .unsubscribed_channels(channelsUnsubscribedArray()).customer_id("").build();
    }

    private ChannelSubsParams channelSubsParams1() {
        return ChannelSubsParams.builder().subscribed_channels(channelsSubscribedArray())
                .unsubscribed_channels(channelsUnsubscribedArray()).customer_id(preferencesManager.get(Constants.CUSTOMER_ID)).build();
    }

    private JsonArray channelsSubscribedArray() {

        JsonArray channels_list = new JsonArray();
        for (int i = 0; i < subscribedChannelsArrayList.size(); i++) {
            channels_list.add(subscribedChannelsArrayList.get(i).toString());
        }

        return channels_list;

    }


    private JsonArray channelsUnsubscribedArray() {

        JsonArray channels_list = new JsonArray();
        for (int i = 0; i < unsubscribedChannelArrayList.size(); i++) {
            channels_list.add(unsubscribedChannelArrayList.get(i).toString());
        }

        return channels_list;
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
