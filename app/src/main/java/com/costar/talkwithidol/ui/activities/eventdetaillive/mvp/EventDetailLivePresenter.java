package com.costar.talkwithidol.ui.activities.eventdetaillive.mvp;


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
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
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

public class EventDetailLivePresenter {
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    public static String channelId;
    private final EventDetailLiveView eventDetailLiveView;
    private final EventDetailLiveModel eventDetailLiveModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EventDetailLivePresenter(EventDetailLiveView eventDetailLiveView, EventDetailLiveModel eventDetailLiveModel, PreferencesManager preferencesManager) {
        this.eventDetailLiveView = eventDetailLiveView;
        this.eventDetailLiveModel = eventDetailLiveModel;
        this.preferencesManager = preferencesManager;

    }

    public void onCreate() {
        getEventsDetail();
        freeTrialClicked();
        subscribeClicked();
        payClicked();
        subscriptionStepTwo();
        formValidation();
        LikeClicked();

    }

    private void LikeClicked() {


        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess()&&likeEntityReponse.getData()!=null) {
                    if (likeEntityReponse.getData().getStatus().equals("added")) {
                        eventDetailLiveView.setAddTowatchlist(true);
                    } else {
                        eventDetailLiveView.setAddTowatchlist(false);
                    }
                } else {
                    eventDetailLiveView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDetailLiveView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

                eventDetailLiveView.showLoadingDialog(false);
            }
        };

        eventDetailLiveView.addToWatchListObservable()
                .map(__ -> addToWatchlistParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailLiveModel::getAddToWatchList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(EventDetailLiveActivity.videoId).type("node")
                .build();
    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    eventDetailLiveView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(eventDetailLiveView.creditCardDialog.cardnameCharSequenceFlowable(),
                eventDetailLiveView.creditCardDialog.cardnoCharSequenceFlowable(),
                eventDetailLiveView.creditCardDialog.monthCharSequenceFlowable(),
                eventDetailLiveView.creditCardDialog.yearCharSequenceFlowable(),
                eventDetailLiveView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(eventDetailLiveView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        eventDetailLiveView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(eventDetailLiveView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        eventDetailLiveView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(eventDetailLiveView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        eventDetailLiveView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(eventDetailLiveView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        eventDetailLiveView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(eventDetailLiveView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        eventDetailLiveView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(eventDetailLiveView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        eventDetailLiveView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    public  void onPause(){
        if( eventDetailLiveView.brightcove_video_view!=null)
            eventDetailLiveView.brightcove_video_view.pause();
    }


    // get explore news list
    private void getEventsDetail() {

        eventDetailLiveView.showLoadingDialog(true);

        DisposableObserver<EventsDetailResponse> disposableObserver = new DisposableObserver<EventsDetailResponse>() {

            @Override
            public void onNext(EventsDetailResponse eventsDetailResponse) {

                if(eventsDetailResponse.getSuccess()&&eventsDetailResponse.getData()!=null) {
                    if (eventsDetailResponse.getData().getAccess().equals("denied")) {
                        channelId = eventsDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                        subscribedgateway = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                        if (subscribedgateway.equals("apple")) {
                            isApple = true;
                        }
                        subscribedmessage = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                    }

//                    if(eventsDetailResponse.getData().getType())
                    if (eventsDetailResponse.getData().getEventState().equalsIgnoreCase("live")) {
                        eventDetailLiveView.setEventsDetail(eventsDetailResponse);
                    }else{
                        eventDetailLiveView.setEventsDetail(eventsDetailResponse);
                        getEventsDetail();
                    }

                }else
                {
                    if(eventsDetailResponse.getMessage().contains(Constants.AccessDenied)){
                        eventDetailLiveModel.saveData(Constants.TOKEN,null);
                        HomePageActivity.startLogout(eventDetailLiveView.activity);
                    }
                    eventDetailLiveView.showMessage(eventsDetailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //eventDetailLiveView.showMessage("Time Out");

                    getEventsDetail();
                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDetailLiveView.showLoadingDialog(false);
            }
        };

        eventDetailLiveModel.getEventDetailObservable(EventDetailLiveActivity.videoId, eventDetailLiveModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void freeTrialClicked() {

        DisposableObserver<FreeTrialResponse> disposableObserver = new DisposableObserver<FreeTrialResponse>() {

            @Override
            public void onNext(FreeTrialResponse freeTrialResponse) {
                if (freeTrialResponse.getSuccess()&&freeTrialResponse.getData()!=null) {
                    eventDetailLiveView.showMessage(freeTrialResponse.getData().getMessage());
                    eventDetailLiveView.deniedDialog.showDialog(false);

                } else {
                    eventDetailLiveView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailLiveView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ ->eventDetailLiveView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailLiveModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
               .doOnEach(__ -> eventDetailLiveView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {


        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()&&subscriptionStepOneResponse.getData()!=null) {
                    if (isApple) {
                        eventDetailLiveView.webSubsDialog.showDialog(true);
                        eventDetailLiveView.webSubsDialog.setData(subscribedmessage);

                    } else {
                        eventDetailLiveView.confirmDialog.showDialog(true);
                        eventDetailLiveView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        eventDetailLiveView.confirmDialog.setData(subscriptionStepOneResponse);
                    }

                } else {
                    eventDetailLiveView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailLiveView.deniedDialog.subscribeNowButtonObservable()
                 .doOnNext(__ ->eventDetailLiveView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailLiveModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailLiveView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {
        eventDetailLiveView.showProgressDialog(true);

        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess()&&paydockConfigurationResponse.getData()!=null) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    eventDetailLiveView.creditCardDialog.showDialog(true);
                } else {
                    eventDetailLiveView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
              eventDetailLiveView.showProgressDialog(false);
            }
        };
        eventDetailLiveModel.getPaydockConfigurationObservable()
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
                    eventDetailLiveView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDetailLiveView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();

            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailLiveView.creditCardDialog.payButtonObservable()
                 .doOnNext(__ ->eventDetailLiveView.showProgressDialog(true))
                .map(__ -> eventDetailLiveView
                .creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailLiveModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailLiveView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {

        eventDetailLiveView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    eventDetailLiveView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    eventDetailLiveView.confirmDialog.showDialog(false);
                    eventDetailLiveView.creditCardDialog.showDialog(false);
                    eventDetailLiveView.deniedDialog.showDialog(false);
                    eventDetailLiveView.isDialog = false;
                } else {
                    eventDetailLiveView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDetailLiveView.showProgressDialog(false);
            }
        };
        eventDetailLiveModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess()&&subscriptionStepTwoResponse.getData()!=null) {
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        eventDetailLiveView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        eventDetailLiveView.confirmDialog.showDialog(false);
                        eventDetailLiveView.creditCardDialog.showDialog(false);
                        eventDetailLiveView.deniedDialog.showDialog(false);
                        eventDetailLiveView.isDialog = false;
                    }
                } else {
                    eventDetailLiveView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDetailLiveView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDetailLiveView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDetailLiveView.showMessage("Please check your network connection");

                } else {
                    eventDetailLiveView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDetailLiveView.confirmButtonObservable()
                 .doOnNext(__ ->eventDetailLiveView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDetailLiveModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDetailLiveView.showProgressDialog(false))
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
        if( eventDetailLiveView.brightcove_video_view!=null)
            eventDetailLiveView.brightcove_video_view.stopPlayback();
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
