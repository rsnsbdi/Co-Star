package com.costar.talkwithidol.ui.activities.communitydetail.mvp;


import android.view.View;

import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialParams;
import com.costar.talkwithidol.app.network.models.FreeTrial.FreeTrialResponse;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PaydockConfig.PaydockConfigurationResponse;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.PostComment.AddCommentResponse;
import com.costar.talkwithidol.app.network.models.ReportId;
import com.costar.talkwithidol.app.network.models.ReportParams;
import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse.SubscriptionStepOneResponse;
import com.costar.talkwithidol.app.network.models.SubscriptionStepTwoResponse.SubscriptionStepTwoResponse;
import com.costar.talkwithidol.app.network.models.commentsResponse.CommentsResponse;
import com.costar.talkwithidol.app.network.models.commentsResponse.Datum;
import com.costar.talkwithidol.app.network.models.communityDetail.CommunityDetailResponse;
import com.costar.talkwithidol.app.network.models.reportResponse.ReportResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelSubsParams;
import com.costar.talkwithidol.ui.fragments.newslist.EndlessRecyclerViewScrollListener;
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

public class CommunityDtailPresenter {
    public static String channelId;
    public static String subscribedgateway = null;
    public static SubscribedMessage subscribedmessage = null;
    public static Boolean isApple = false;
    public static String parentCommentid;

    private final CommunityDtailView communityDtailView;
    private final CommunityDtailModel communityDtailModel;
    PreferencesManager preferencesManager;
    ArrayList<Datum> commentList = new ArrayList<>();
    int page = 0;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;

    public CommunityDtailPresenter(CommunityDtailView communityDtailView, CommunityDtailModel communityDtailModel, PreferencesManager preferencesManager) {
        this.communityDtailView = communityDtailView;
        this.communityDtailModel = communityDtailModel;
        this.preferencesManager = preferencesManager;

    }


    public void onCreate() {
        getCommunityDetail();
        freeTrialClicked();
        subscribeClicked();
        payClicked();
        subscriptionStepTwo();
        onReplyClick();
        onViewAllClick();
        postReplyClicked();
        postCommentCLicked();
        formValidation();
        LikeClickedNoComment();


        RxBus.getInstance().toObservable().subscribe(o -> {
            if (o instanceof String) {
                LikeClicked();
            } else if (o instanceof ReportParams) {
                sendReport((ReportParams) o);
            } else if (o instanceof ReportId) {
                if (!communityDtailView.activity.isFinishing()) {
                    communityDtailView.showReportDialog(((ReportId) o));
                }
            }
        });


    }


    private void formValidation() {


        //call when it is subscribe
        DisposableSubscriber<Boolean> disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean isFormValid) {
                if (isFormValid) {

                    communityDtailView.creditCardDialog.pay.setEnabled(true);
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


        Flowable.combineLatest(communityDtailView.creditCardDialog.cardnameCharSequenceFlowable(),
                communityDtailView.creditCardDialog.cardnoCharSequenceFlowable(),
                communityDtailView.creditCardDialog.monthCharSequenceFlowable(),
                communityDtailView.creditCardDialog.yearCharSequenceFlowable(),
                communityDtailView.creditCardDialog.ccvCharSequenceFlowable(),


                (cardname, cardNo, month, year, ccv) -> {

                    boolean isCardNameValid = !isEmpty(communityDtailView.creditCardDialog.cardname.getText().toString());
                    if (!isCardNameValid)
                        communityDtailView.creditCardDialog.setCardNameError("Card name is Empty");

                    boolean isCardnovalid = !isEmpty(communityDtailView.creditCardDialog.cardno.getText().toString());
                    if (!isCardnovalid)
                        communityDtailView.creditCardDialog.setCarnoError("Card number is Empty");

                    boolean isMnthValid = !isEmpty(communityDtailView.creditCardDialog.month.getText().toString());
                    if (!isMnthValid)
                        communityDtailView.creditCardDialog.setMonthError("Expiry Month is Empty");


                    boolean isYearValid = !isEmpty(communityDtailView.creditCardDialog.year.getText().toString());
                    if (!isYearValid)
                        communityDtailView.creditCardDialog.setYearError("Expiry Year is Empty");


                    boolean isCcvValid = !isEmpty(communityDtailView.creditCardDialog.ccv.getText().toString());
                    if (!isCcvValid)
                        communityDtailView.creditCardDialog.setCCVError("CCV number is Empty");

                    boolean isCcvNoValid = !(communityDtailView.creditCardDialog.ccv.getText().toString().length() < 3);
                    if (!isCcvNoValid)
                        communityDtailView.creditCardDialog.setCCVError("CCV number not valid. Enter 3 digit number.");


                    return isCardNameValid && isCardnovalid && isMnthValid && isYearValid && isCcvValid && isCcvNoValid;

                }).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //like clicked
    private void LikeClicked() {


        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess()) {
                    communityDtailView.setLike(likeEntityReponse);
                } else {
                    communityDtailView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    communityDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailModel.getLikeEntittyObservable(likeEntityParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
//        communityDtailView.likeClickObservable()
//                .map(__ -> likeEntityParams())
//                .observeOn(Schedulers.io())
//                .switchMap(communityDtailModel::getLikeEntittyObservable)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(disposableObserver);
    }

    //like clicked
    private void LikeClickedNoComment() {


        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess()) {
                    communityDtailView.setLikeNC(likeEntityReponse);
                } else {
                    communityDtailView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    communityDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.likeclickobservable()
                .map(__ -> likeEntityParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::getLikeEntittyObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(CommunityDtailActivity.videoId).type("node")
                .build();
    }

    private void sendReport(ReportParams reportParams) {


        DisposableObserver<ReportResponse> disposableObserver = new DisposableObserver<ReportResponse>() {

            @Override
            public void onNext(ReportResponse reportResponse) {


                if (reportResponse.getSuccess()) {
                    communityDtailView.showMessage(reportResponse.getData().getMessage());
                    getCommunityDetail1();

                } else {

                    if (reportResponse.getMessage().contains(Constants.AccessDenied)) {
                        communityDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(communityDtailView.activity);
                    }
                    communityDtailView.showMessage(reportResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                    getCommunityDetail();
                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        communityDtailModel.reportObservable(reportParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore news list
    private void getCommunityDetail() {


        communityDtailView.showLoadingDialog(true);
        DisposableObserver<CommunityDetailResponse> disposableObserver = new DisposableObserver<CommunityDetailResponse>() {

            @Override
            public void onNext(CommunityDetailResponse communityDetailResponse) {


                if (communityDetailResponse.getSuccess()) {
                    getCommunityComments();

                    if (communityDetailResponse.getData() != null) {
                        communityDtailView.setCommunityDetail(communityDetailResponse);
                        if (communityDetailResponse.getData().getAccess().equals("denied")) {
                            channelId = communityDetailResponse.getData().getDenied().getFreetrial().getChannelId();

                            subscribedgateway = communityDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                            if (subscribedgateway.equals("apple")) {
                                isApple = true;
                            }
                            subscribedmessage = communityDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                        }


                    }
                } else {

                    if (communityDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        communityDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(communityDtailView.activity);
                    }
                    communityDtailView.showMessage(communityDetailResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                    getCommunityDetail();
                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        communityDtailModel.getCommunityDetailObservable(CommunityDtailActivity.videoId, communityDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    // get explore news list
    private void getCommunityComments() {


        DisposableObserver<CommentsResponse> disposableObserver = new DisposableObserver<CommentsResponse>() {

            @Override
            public void onNext(CommentsResponse commentsResponse) {


                if (commentsResponse.getSuccess()) {
                    if (commentsResponse.getData() != null) {
                        commentList.addAll(commentsResponse.getData());
                        communityDtailView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(communityDtailView.layoutManager()) {
                            @Override
                            public void onLoadMore(int page, int totalItemsCount) {
                                getCommunityComments(page);
                            }
                        });
                        communityDtailView.setCommunityComments(commentList);
                    } else {
                        communityDtailView.recyclerView.setVisibility(View.GONE);
                        communityDtailView.detail.setVisibility(View.VISIBLE);
                    }

                } else {

                    if (!commentsResponse.getEmpty())
                        communityDtailView.showMessage(commentsResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                communityDtailView.showLoadingDialog(false);

            }
        };

        communityDtailModel.getCommunityCommentsObservable(page, CommunityDtailActivity.videoId, communityDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getCommunityComments(int page) {

        DisposableObserver<CommentsResponse> disposableObserver = new DisposableObserver<CommentsResponse>() {

            @Override
            public void onNext(CommentsResponse commentsResponse) {

                if (commentsResponse.getSuccess()) {

                    if (commentsResponse.getData() != null && commentsResponse.getData().size() != 0) {
                        commentList.addAll(commentsResponse.getData());
                        communityDtailView.setCommunityComments(commentList);
                    }

                } else {

                    if (commentsResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(communityDtailView.activity);
                    }
                    if (!commentsResponse.getEmpty())
                        communityDtailView.showMessage(commentsResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        communityDtailModel.getCommunityCommentsObservable(page, CommunityDtailActivity.videoId, communityDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void freeTrialClicked() {

        DisposableObserver<FreeTrialResponse> disposableObserver = new DisposableObserver<FreeTrialResponse>() {

            @Override
            public void onNext(FreeTrialResponse freeTrialResponse) {
                if (freeTrialResponse.getSuccess()) {
                    communityDtailView.showMessage(freeTrialResponse.getData().getMessage());
                    communityDtailView.deniedDialog.showDialog(false);
                } else {
                    communityDtailView.showMessage(freeTrialResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.deniedDialog.freeTrialButtonObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> freeTrialParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::freeTrialActivationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void subscribeClicked() {

        DisposableObserver<SubscriptionStepOneResponse> disposableObserver = new DisposableObserver<SubscriptionStepOneResponse>() {

            @Override
            public void onNext(SubscriptionStepOneResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    if (isApple) {
                        communityDtailView.webSubsDialog.showDialog(true);
                        communityDtailView.webSubsDialog.setData(subscribedmessage);
                    } else {
                        communityDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                        communityDtailView.confirmDialog.showDialog(true);
                        communityDtailView.confirmDialog.setData(subscriptionStepOneResponse);
                    }

                } else {
                    communityDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.deniedDialog.subscribeNowButtonObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::subscriptionStepOne)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
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
                        communityDtailView.showMessage(subscriptionStepTwoResponse.getData().getMessage());
                        communityDtailView.confirmDialog.showDialog(false);
                        communityDtailView.deniedDialog.showDialog(false);
                        communityDtailView.isDialog = false;
                    }
                } else {
                    communityDtailView.showMessage(subscriptionStepTwoResponse.getData().getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.confirmButtonObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> channelSubsParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::subscriptionStepTwo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    private void getPaydockConfig() {

        communityDtailView.showProgressDialog(true);
        DisposableObserver<PaydockConfigurationResponse> disposableObserver = new DisposableObserver<PaydockConfigurationResponse>() {

            @Override
            public void onNext(PaydockConfigurationResponse paydockConfigurationResponse) {
                if (paydockConfigurationResponse.getSuccess()) {
                    preferencesManager.save(Constants.PAYDOCK_SECRET_KEY, paydockConfigurationResponse.getData().getSecretKey());
                    preferencesManager.save(Constants.PAYDOCK_PUBLIC_KEY, paydockConfigurationResponse.getData().getPublicKey());
                    preferencesManager.save(Constants.GATEWAYID, paydockConfigurationResponse.getData().getGatewayId());
                    preferencesManager.save(Constants.PAYDOCKENDPOINT, paydockConfigurationResponse.getData().getEndpoint());
                    communityDtailView.creditCardDialog.showDialog(true);
                } else {
                    communityDtailView.showMessage(paydockConfigurationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                communityDtailView.showProgressDialog(false);
            }
        };
        communityDtailModel.getPaydockConfigurationObservable()
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
                    communityDtailView.showMessage("Payment Error");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    communityDtailView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
                payClicked();
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.creditCardDialog.payButtonObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> communityDtailView.creditCardDialog.creditCardParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::PaydockCustomerResponseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void newSubscription() {

        communityDtailView.showProgressDialog(true);

        DisposableObserver<SubscriptionStepTwoResponse> disposableObserver = new DisposableObserver<SubscriptionStepTwoResponse>() {

            @Override
            public void onNext(SubscriptionStepTwoResponse subscriptionStepOneResponse) {
                if (subscriptionStepOneResponse.getSuccess()) {
                    communityDtailView.showMessage(subscriptionStepOneResponse.getData().getMessage());
                    communityDtailView.confirmDialog.showDialog(false);
                    communityDtailView.creditCardDialog.showDialog(false);
                    communityDtailView.deniedDialog.showDialog(false);
                    communityDtailView.isDialog = false;
                } else {
                    communityDtailView.showMessage(subscriptionStepOneResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                communityDtailView.showProgressDialog(false);
            }
        };
        communityDtailModel.CustomerNewSubscription(channelSubsParams1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private void onReplyClick() {
        DisposableObserver<String> disposableObserver = getReplyClickObserver();
        communityDtailView.getReplyClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<String> getReplyClickObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String id) {

                communityDtailView.showReplyLayout(id);

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


    private void onViewAllClick() {
        DisposableObserver<String> disposableObserver = getViewAllClickObserver();
        communityDtailView.getViewAllClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<String> getViewAllClickObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String id) {
                communityDtailView.startChildComment(id);
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


    private void postReplyClicked() {

        DisposableObserver<AddCommentResponse> disposableObserver = new DisposableObserver<AddCommentResponse>() {

            @Override
            public void onNext(AddCommentResponse addCommentResponse) {
                if (addCommentResponse.getSuccess()) {
                    communityDtailView.hideReplylayout();
                    communityDtailView.replyPost.getText().clear();
                    communityDtailView.reply.setClickable(true);
                    getCommunityDetail1();
                } else {
                    communityDtailView.showMessage(addCommentResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.postReplyCLickObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> communityDtailView.postCommentParams())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::postCommentObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void postCommentCLicked() {
        DisposableObserver<AddCommentResponse> disposableObserver = new DisposableObserver<AddCommentResponse>() {

            @Override
            public void onNext(AddCommentResponse addCommentResponse) {
                if (addCommentResponse.getSuccess()) {
                    communityDtailView.comment.setClickable(true);
                    communityDtailView.commentDescription.getText().clear();
                    getCommunityDetail1();
                } else {
                    communityDtailView.showMessage(addCommentResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        communityDtailView.postCommentClickObservable()
                .doOnNext(__ -> communityDtailView.showProgressDialog(true))
                .map(__ -> communityDtailView.postCommentParams1())
                .observeOn(Schedulers.io())
                .switchMap(communityDtailModel::postCommentObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> communityDtailView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }


    private void getCommunityDetail1() {


//        communityDtailView.showLoadingView(true);
        DisposableObserver<CommunityDetailResponse> disposableObserver = new DisposableObserver<CommunityDetailResponse>() {

            @Override
            public void onNext(CommunityDetailResponse communityDetailResponse) {


                if (communityDetailResponse.getSuccess()) {
                    getCommunityComments1();

                    if (communityDetailResponse.getData() != null) {
                        communityDtailView.setCommunityDetail(communityDetailResponse);
                        if (communityDetailResponse.getData().getAccess().equals("denied")) {
                            channelId = communityDetailResponse.getData().getDenied().getFreetrial().getChannelId();
                            subscribedgateway = communityDetailResponse.getData().getDenied().getSubscribe().getSubscribed_gateway();
                            subscribedmessage = communityDetailResponse.getData().getDenied().getSubscribe().getSubscribed_message();
                            if (subscribedgateway.equals("apple")) {
                                isApple = true;
                            }
                        }
                    }
                } else {

                    if (communityDetailResponse.getMessage().contains(Constants.AccessDenied)) {
                        communityDtailModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(communityDtailView.activity);
                    }
                    communityDtailView.showMessage(communityDetailResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                    getCommunityDetail();
                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        communityDtailModel.getCommunityDetailObservable(CommunityDtailActivity.videoId, communityDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getCommunityComments1() {


        DisposableObserver<CommentsResponse> disposableObserver = new DisposableObserver<CommentsResponse>() {

            @Override
            public void onNext(CommentsResponse commentsResponse) {


                if (commentsResponse.getSuccess()) {
                    if (commentsResponse.getData() != null) {
                        commentList.clear();
                        commentList.addAll(commentsResponse.getData());
                        communityDtailView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(communityDtailView.layoutManager()) {
                            @Override
                            public void onLoadMore(int page, int totalItemsCount) {
                                getCommunityComments(page);
                            }
                        });
                        communityDtailView.setCommunityComments(commentList);
                    } else {
                        communityDtailView.detail.setVisibility(View.VISIBLE);
                        communityDtailView.recyclerView.setVisibility(View.GONE);
                    }

                } else {

                    if (!commentsResponse.getEmpty())
                        communityDtailView.showMessage(commentsResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityDtailView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityDtailView.showMessage("Please check your network connection");

                } else {
                    communityDtailView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
//                communityDtailView.showLoadingView(false);

            }
        };

        communityDtailModel.getCommunityCommentsObservable(page, CommunityDtailActivity.videoId, communityDtailModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
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
