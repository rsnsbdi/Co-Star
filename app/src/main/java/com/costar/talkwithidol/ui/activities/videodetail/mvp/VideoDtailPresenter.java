package com.costar.talkwithidol.ui.activities.videodetail.mvp;


import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.videoDetail.VideoDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
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

public class VideoDtailPresenter {
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    public static String channelId;
    private final VideoDtailView videoDtailView;
    private final VideoDtailModel videoDtailModel;
    PreferencesManager preferencesManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;

    public VideoDtailPresenter(VideoDtailView videoDtailView, VideoDtailModel videoDtailModel, PreferencesManager preferencesManager) {
        this.videoDtailView = videoDtailView;
        this.videoDtailModel = videoDtailModel;
        this.preferencesManager = preferencesManager;

    }


    public void onCreate() {
        getVideoDetail();
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
                    videoDtailView.setLike(likeEntityReponse);
                } else {
                    videoDtailView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    videoDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };

        videoDtailView.likeClickObservable()
                .map(__ -> likeEntityParams())
                .observeOn(Schedulers.io())
                .switchMap(videoDtailModel::getLikeEntittyObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(VideoDtailActivity.videoId).type("node")
                .build();
    }

    public void onPause() {
        if (videoDtailView.brightcove_video_view != null)
            videoDtailView.brightcove_video_view.pause();
    }

    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    videoDtailView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(videoDtailView.creditCardDialog.cardnameCharSequenceFlowable(),
                videoDtailView.creditCardDialog.cardnoCharSequenceFlowable(),
                videoDtailView.creditCardDialog.monthCharSequenceFlowable(),
                videoDtailView.creditCardDialog.yearCharSequenceFlowable(),
                videoDtailView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(videoDtailView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        videoDtailView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(videoDtailView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        videoDtailView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(videoDtailView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        videoDtailView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(videoDtailView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        videoDtailView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(videoDtailView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        videoDtailView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(videoDtailView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        videoDtailView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore video list
    private void getVideoDetail() {


        videoDtailView.showLoadingDialog(true);

        DisposableObserver<VideoDetailResponse> disposableObserver = new DisposableObserver<VideoDetailResponse>() {

            @Override
            public void onNext(VideoDetailResponse videoDetailResponse) {
                if (videoDetailResponse.getSuccess() && videoDetailResponse.getData() != null) {
                    videoDtailView.setVideoDtail(videoDetailResponse);
                    if (videoDetailResponse.getData().getAccess().equals("denied")) {
                        channelId = videoDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                        subscribedgateway = videoDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                        if (subscribedgateway.equals("apple")) {
                            isApple = true;
                        }
                        subscribedmessage = videoDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                    }
                } else {
                    if (videoDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        videoDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(videoDtailView.activity);
                    }
                    videoDtailView.showMessage(videoDetailResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //videoDtailView.showMessage("Time Out");
                    getVideoDetail();

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                videoDtailView.showLoadingDialog(false);
            }
        };

        videoDtailModel.getVideoObservable(VideoDtailActivity.videoId, videoDtailModel.getData(Constants.COOKIE))
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
                    videoDtailView.showMessage(freeTrialResponse.getData().getMessage());
                    videoDtailView.deniedDialog.showDialog(false);

                } else {
                    videoDtailView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        videoDtailView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ -> videoDtailView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(videoDtailModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> videoDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {
        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    if (isApple) {
                        videoDtailView.webSubsDialog.showDialog(true);
                        videoDtailView.webSubsDialog.setData(subscribedmessage);

                    } else {
                        videoDtailView.confirmDialog.showDialog(true);
                        videoDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        videoDtailView.confirmDialog.setData(subscriptionStepOneResponse);
                    }
//                    }
                } else {
                    videoDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        videoDtailView.deniedDialog.subscribeNowButtonObservable()
                .doOnNext(__ -> videoDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(videoDtailModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> videoDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getPaydockConfig() {

        videoDtailView.showProgressDialog(true);
        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess()) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    videoDtailView.creditCardDialog.showDialog(true);
                } else {
                    videoDtailView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                videoDtailView.showProgressDialog(false);
            }
        };
        videoDtailModel.getPaydockConfigurationObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void payClicked() {


        DisposableObserver<PaydockCustomerResponse> disposableObserver = new DisposableObserver<PaydockCustomerResponse>() {

            @Override
            public void onNext(PaydockCustomerResponse paydockCustomerResponse) {
                if (paydockCustomerResponse.getStatus() == 201) {
                    preferencesManager.save(Constants.CUSTOMER_ID, paydockCustomerResponse.getResource().getData().getId());
                    newSubscription();

                } else {
                    videoDtailView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    videoDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();

            }

            @Override
            public void onComplete() {

            }
        };
        videoDtailView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> videoDtailView.showProgressDialog(true))
                .map(__ -> videoDtailView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(videoDtailModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> videoDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {


        videoDtailView.showProgressDialog(true);
        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    videoDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    videoDtailView.confirmDialog.showDialog(false);
                    videoDtailView.creditCardDialog.showDialog(false);
                    videoDtailView.deniedDialog.showDialog(false);
                    videoDtailView.isDialog = false;
                } else {
                    videoDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                videoDtailView.showProgressDialog(false);
            }
        };
        videoDtailModel.subscriptionStepTwo(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void subscriptionStepTwo() {


        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepTwoResponse) {
                if (subscriptionStepTwoResponse.getSuccess() && subscriptionStepTwoResponse.getData() != null) {
                    if (subscriptionStepTwoResponse.getData().getSubscription().equals("payment_required")) {
                        getPaydockConfig();
                    } else {
                        videoDtailView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        videoDtailView.confirmDialog.showDialog(false);
                        videoDtailView.creditCardDialog.showDialog(false);
                        videoDtailView.deniedDialog.showDialog(false);
                        videoDtailView.isDialog = false;
                    }
                } else {
                    videoDtailView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoDtailView.showMessage("Please check your network connection");

                } else {
                    videoDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        videoDtailView.confirmDialog.confirmButtonObservable()
                .doOnNext(__ -> videoDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(videoDtailModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> videoDtailView.showProgressDialog(false))
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

        if (videoDtailView.brightcove_video_view != null)
            videoDtailView.brightcove_video_view.pause();
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
