package com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp;


import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.EventDetailPexipActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;
import com.google.gson.JsonArray;
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

public class EventDetailPexipPresenter {
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    public static String channelId;
    private final EventDetailPexipView eventDetailPexipView;
    private final EventDetailPexipModel eventDetailPexipModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EventDetailPexipPresenter(EventDetailPexipView eventDetailPexipView, EventDetailPexipModel eventDetailPexipModel, PreferencesManager preferencesManager) {
        this.eventDetailPexipView = eventDetailPexipView;
        this.eventDetailPexipModel = eventDetailPexipModel;
        this.preferencesManager = preferencesManager;

    }

    public void onCreate() {
        getEventsDetail();
        freeTrialClicked();
        subscribeClicked();
        payClicked();
        subscriptionStepTwo();
        //  Rlclisked();
        formValidation();
        LikeClicked();

    }

    private void LikeClicked() {


        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess() && likeEntityReponse.getData() != null) {
                    if (likeEntityReponse.getData().getStatus().equals("added")) {
                        eventDetailPexipView.setAddTowatchlist(true);
                    } else {
                        eventDetailPexipView.setAddTowatchlist(false);
                    }
                } else {
                    eventDetailPexipView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDetailPexipView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };

        eventDetailPexipView.addToWatchListObservable()
                .map(__ -> addToWatchlistParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailPexipModel::getAddToWatchList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(EventDetailPexipActivity.videoId).type("node")
                .build();
    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    eventDetailPexipView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(eventDetailPexipView.creditCardDialog.cardnameCharSequenceFlowable(),
                eventDetailPexipView.creditCardDialog.cardnoCharSequenceFlowable(),
                eventDetailPexipView.creditCardDialog.monthCharSequenceFlowable(),
                eventDetailPexipView.creditCardDialog.yearCharSequenceFlowable(),
                eventDetailPexipView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(eventDetailPexipView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        eventDetailPexipView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(eventDetailPexipView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        eventDetailPexipView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(eventDetailPexipView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        eventDetailPexipView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(eventDetailPexipView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        eventDetailPexipView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(eventDetailPexipView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        eventDetailPexipView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(eventDetailPexipView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        eventDetailPexipView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    /*  public void Rlclisked(){
          eventDetailPexipView.rlTapObservable().subscribe(o->getEventsDetail());
      }*/
    // get explore news list
    private void getEventsDetail() {
        eventDetailPexipView.showLoadingDialog(true);
        DisposableObserver<EventsDetailResponse> disposableObserver = new DisposableObserver<EventsDetailResponse>() {

            @Override
            public void onNext(EventsDetailResponse eventsDetailResponse) {

                if (eventsDetailResponse.getSuccess() == true && eventsDetailResponse.getData() != null) {
                    if (eventsDetailResponse.getData().getAccess().equals("denied")) {
                        channelId = eventsDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                        subscribedgateway = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                        if (subscribedgateway.equals("apple")) {
                            isApple = true;
                        }
                        subscribedmessage = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                    }
                    eventDetailPexipView.setEventsDetail(eventsDetailResponse);

                } else {

                    if (eventsDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        eventDetailPexipModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(eventDetailPexipView.activity);
                    }
                    eventDetailPexipView.showMessage(eventsDetailResponse.getMessage());
                    // eventDetailPexipView.ShowRltap(true);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //eventDetailPexipView.showMessage("Time Out");

                    getEventsDetail();
                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDetailPexipView.showLoadingDialog(false);
            }
        };

        eventDetailPexipModel.getEventDetailObservable(EventDetailPexipActivity.videoId, eventDetailPexipModel.getData(Constants.COOKIE))
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
                    eventDetailPexipView.showMessage(freeTrialResponse.getData().getMessage());
                    eventDetailPexipView.deniedDialog.showDialog(false);

                } else {
                    eventDetailPexipView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailPexipView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ -> eventDetailPexipView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailPexipModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailPexipView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {

        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess() && subscriptionStepOneResponse.getData() != null) {
                    if (isApple) {
                        eventDetailPexipView.webSubsDialog.showDialog(true);
                        eventDetailPexipView.webSubsDialog.setData(subscribedmessage);
                    } else {
                        eventDetailPexipView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        eventDetailPexipView.confirmDialog.showDialog(true);
                        eventDetailPexipView.confirmDialog.setData(subscriptionStepOneResponse);
                    }
                } else {
                    eventDetailPexipView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailPexipView.deniedDialog.subscribeNowButtonObservable()
                .doOnNext(__ -> eventDetailPexipView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailPexipModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(__ -> eventDetailPexipView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {
        eventDetailPexipView.showProgressDialog(true);
        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess() && paydockConfigurationResponse.getData() != null) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    eventDetailPexipView.creditCardDialog.showDialog(true);
                } else {
                    eventDetailPexipView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDetailPexipView.showProgressDialog(false);
            }
        };
        eventDetailPexipModel.getPaydockConfigurationObservable()
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
                    eventDetailPexipView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDetailPexipView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();

            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailPexipView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> eventDetailPexipView.showProgressDialog(true))
                .map(__ -> eventDetailPexipView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailPexipModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailPexipView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {
        eventDetailPexipView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    eventDetailPexipView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    eventDetailPexipView.confirmDialog.showDialog(false);
                    eventDetailPexipView.creditCardDialog.showDialog(false);
                    eventDetailPexipView.deniedDialog.showDialog(false);
                    eventDetailPexipView.isDialog = false;
                } else {
                    eventDetailPexipView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDetailPexipView.showProgressDialog(false);
            }
        };
        eventDetailPexipModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess() && subscriptionStepTwoResponse.getData() != null) {
                    eventDetailPexipView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        eventDetailPexipView.confirmDialog.showDialog(false);
                        eventDetailPexipView.creditCardDialog.showDialog(false);
                        eventDetailPexipView.deniedDialog.showDialog(false);
                        eventDetailPexipView.isDialog = false;
                    }
                } else {
                    eventDetailPexipView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailPexipView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailPexipView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailPexipView.showMessage("Please check your network connection");

                } else {
                    eventDetailPexipView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailPexipView.confirmButtonObservable()
                .doOnNext(__ -> eventDetailPexipView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailPexipModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailPexipView.showProgressDialog(false))
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


    public void onBackPressed() {
        eventDetailPexipView.pexView.evaluateFunction("disconnect");
        eventDetailPexipView.activity.finish();
    }

    public void onDestroyView() {
        eventDetailPexipView.pexView.evaluateFunction("disconnect");
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
