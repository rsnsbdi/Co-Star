package com.costar.talkwithidol.ui.activities.eventdetail.mvp;


import android.os.Handler;
import android.text.Html;

import com.bumptech.glide.Glide;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.QuestionParams;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.app.network.models.questionSubmit.SubmitQuestionResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
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
import static com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailView.isDialogA;

public class EventDtailPresenter {
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    public static String channelId;
    private final EventDtailView eventDtailView;
    private final EventDtailModel eventDtailModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;


    public EventDtailPresenter(EventDtailView eventDtailView, EventDtailModel eventDtailModel, PreferencesManager preferencesManager) {
        this.eventDtailView = eventDtailView;
        this.eventDtailModel = eventDtailModel;
        this.preferencesManager = preferencesManager;
    }

    public void onCreate() {
        getEventsDetail();
        submitRegister();
        freeTrialClicked();
        subscribeClicked();
        payClicked();
        subscriptionStepTwo();
        backclicked();
        formValidation();
        Glide.with(eventDtailView.activity).load(preferencesManager.get(Constants.AVATERIMAGE))
                .into(eventDtailView.submitQuestionDialog.iv_auther_image);
        LikeClicked();
//        getQuesValidation();

    }


    private void LikeClicked() {

        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess() && likeEntityReponse.getData() != null) {
                    if (likeEntityReponse.getData().getStatus().equals("added")) {
                        eventDtailView.setAddTowatchlist(true);
                    } else {
                        eventDtailView.setAddTowatchlist(false);
                    }
                } else {
                    eventDtailView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // eventDtailView.showMessage("Time Out");

                    LikeClicked();
                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };

        eventDtailView.addToWatchListObservable()
                .map(__ -> addToWatchlistParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::getAddToWatchList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }

    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(EventDtailActivity.videoId).type("node")
                .build();
    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    eventDtailView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(eventDtailView.creditCardDialog.cardnameCharSequenceFlowable(),
                eventDtailView.creditCardDialog.cardnoCharSequenceFlowable(),
                eventDtailView.creditCardDialog.monthCharSequenceFlowable(),
                eventDtailView.creditCardDialog.yearCharSequenceFlowable(),
                eventDtailView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(eventDtailView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        eventDtailView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(eventDtailView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        eventDtailView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(eventDtailView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        eventDtailView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(eventDtailView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        eventDtailView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(eventDtailView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        eventDtailView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(eventDtailView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        eventDtailView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore news list
    private void getEventsDetail() {
        eventDtailView.showLoadingDialog(true);

        DisposableObserver<EventsDetailResponse> disposableObserver = new DisposableObserver<EventsDetailResponse>() {

            @Override
            public void onNext(EventsDetailResponse eventsDetailResponse) {

                if (eventsDetailResponse.getSuccess() && eventsDetailResponse.getData() != null) {
                    if (eventsDetailResponse.getData().getAccess().equals("denied")) {
                        channelId = eventsDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                        subscribedgateway = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                        if (subscribedgateway.equals("apple")) {
                            isApple = true;
                        }
                        subscribedmessage = eventsDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                    }
                    eventDtailView.setEventsDetail(eventsDetailResponse);

                } else {

                    if (eventsDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        eventDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(eventDtailView.activity);
                    }
                    eventDtailView.showMessage(eventsDetailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // eventDtailView.showMessage("Time Out");
                    getEventsDetail();

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");

                } else {
                    eventDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDtailView.showLoadingDialog(false);
            }
        };

        eventDtailModel.getEventDetailObservable(EventDtailActivity.videoId, eventDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //hit api and get response
    private void submitRegister() {

        DisposableObserver<SubmitQuestionResponse> disposableObserver = new DisposableObserver<SubmitQuestionResponse>() {

            @Override
            public void onNext(SubmitQuestionResponse submitQuestionResponse) {
                if (submitQuestionResponse.getSuccess()) {

                    //eventDtailView.submitQuestionDialog.showDialog(false);
//                    eventDtailView.showAfterSubmitDialog(submitQuestionResponse);
                    eventDtailView.afterSubmitDialog.tv_message.setText(Html.fromHtml(submitQuestionResponse.getData().getMessage()));
                    eventDtailView.afterSubmitDialog.showDialog(true);
                    isDialogA = true;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            eventDtailView.submitQuestionDialog.showDialog(false);
                            eventDtailView.afterSubmitDialog.showDialog(false);

                        }
                    }, 3000);
                    getEventsDetail();


                } else {
                    eventDtailView.showMessage(submitQuestionResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    submitRegister();
                    //eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }
                submitRegister();

            }

            @Override
            public void onComplete() {

            }
        };


        eventDtailView.getSendClick()
                .doOnNext(__ -> eventDtailView.showProgressDialog(true))
                .map(__ -> questionParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::submitQuestion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    //initialize the parameters
    private QuestionParams questionParams() {
        return QuestionParams.builder()
                .question(eventDtailView.submitQuestionDialog.getCode())
                .build();
    }


    private void backclicked() {
        eventDtailView.afterSubmitDialog.icBackButtonObservable().subscribe(o -> eventDtailView.finish());
    }

    private void freeTrialClicked() {


        DisposableObserver<FreeTrialResponse> disposableObserver = new DisposableObserver<FreeTrialResponse>() {

            @Override
            public void onNext(FreeTrialResponse freeTrialResponse) {
                if (freeTrialResponse.getSuccess()) {
                    eventDtailView.showMessage(freeTrialResponse.getData().getMessage());
                    eventDtailView.deniedDialog.showDialog(false);
                    getEventsDetail();
                } else {
                    eventDtailView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");

                } else {
                    eventDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDtailView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ -> eventDtailView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {

        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {

                    if (isApple) {
                        eventDtailView.webSubsDialog.showDialog(true);
                        eventDtailView.webSubsDialog.setData(subscribedmessage);

                    } else {
                        eventDtailView.confirmDialog.showDialog(true);
                        eventDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        eventDtailView.confirmDialog.setData(subscriptionStepOneResponse);
                    }
                } else {
                    eventDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");

                } else {
                    eventDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDtailView.deniedDialog.subscribeNowButtonObservable()
                .doOnNext(__ -> eventDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {
        eventDtailView.showProgressDialog(true);

        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess()) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    eventDtailView.creditCardDialog.showDialog(true);
                } else {
                    eventDtailView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");

                } else {
                    eventDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDtailView.showProgressDialog(false);
            }
        };
        eventDtailModel.getPaydockConfigurationObservable()
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
                    eventDtailView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();

            }

            @Override
            public void onComplete() {

            }
        };
        eventDtailView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> eventDtailView.showProgressDialog(true))
                .map(__ -> eventDtailView
                        .creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {

        eventDtailView.showProgressDialog(true);

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    eventDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    eventDtailView.confirmDialog.showDialog(false);
                    eventDtailView.creditCardDialog.showDialog(false);
                    eventDtailView.deniedDialog.showDialog(false);
                    eventDtailView.isDialog = false;
                } else {
                    eventDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView
                            .showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView
                            .showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView
                            .showMessage("Please check your network connection");

                } else {
                    eventDtailView
                            .showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventDtailView.showProgressDialog(false);

            }
        };
        eventDtailModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void subscriptionStepTwo() {

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess()) {
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        eventDtailView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        eventDtailView.confirmDialog.showDialog(false);
                        eventDtailView.creditCardDialog.showDialog(false);
                        eventDtailView.deniedDialog.showDialog(false);
                        eventDtailView.isDialog = false;
                        getEventsDetail();
                    }

                } else {
                    eventDtailView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventDtailView.showMessage("Please check your network connection");

                } else {
                    eventDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        eventDtailView.confirmButtonObservable()
                .doOnNext(__ -> eventDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(eventDtailModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> eventDtailView.showProgressDialog(false))
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

    public void onBackPressed() {

        eventDtailView.afterSubmitDialog.showDialog(false);
        eventDtailView.submitQuestionDialog.showDialog(false);
        eventDtailView.finish();
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
