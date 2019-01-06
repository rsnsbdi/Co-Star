package com.costar.talkwithidol.ui.fragments.notification.mvp;


import android.content.Intent;

import com.costar.talkwithidol.app.network.models.NotificationSeen.NotificationSeenResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.notificationlist.Datum;
import com.costar.talkwithidol.app.network.models.notificationlist.NotificationResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;
import com.costar.talkwithidol.ui.fragments.newslist.EndlessRecyclerViewScrollListener;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class NotificationPresenter {

    private final NotificationView notificationView;
    private final NotificationModel notificationModel;
    Boolean isFirst = true;
    //private FitnessUtils fitnessUtils;
    int page = 0;
    ArrayList<Datum> notificationList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    public static Boolean isClick = true;

    public NotificationPresenter(NotificationView notificationView, NotificationModel notificationModel) {
        this.notificationView = notificationView;
        this.notificationModel = notificationModel;

    }


    public void onCreateView() {

        getNotificationList();
        onDetailClick();
        RxBus.getInstance().toObservable().subscribe(o -> {
            if (o instanceof String) {
                if (o.equals("alert")) {
                    getNotificationList();
                }
            }
        });
// else if (o instanceof Datum){
//                if(!((Datum) o).getState().equals("seen")){
//                    switch (((Datum) o).getLink().getType()){
//                        case "event" :
//                            checkEventState(((Datum) o).getLink().getId());
//                            break;
//                    }
//                }else{
//
//                }
//            }
//            else if (o instanceof NotificationSeenModel) {
//                sendNotificationSeen(((NotificationSeenModel) o).id + "");
//            }else if (o instanceof NotificationEventId){
//                checkEventState(((NotificationEventId) o).id+"");
//            }



    }


    private void onDetailClick() {
        DisposableObserver<Datum> disposableObserver = getDetailClickObserver();
        notificationView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<Datum> getDetailClickObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {
                if (isClick) {
                    isClick = false;
                    if (!datum.getState().equals("seen")) {
                        sendNotificationSeen(datum.getNotifyId());
                    }
                    forwardToDetails(datum);
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

    private void forwardToDetails(Datum datum) {

        switch (datum.getLink().getType()) {
            case "event":
                checkEventState(datum.getLink().getId());
                break;

            case "forum":
                Intent i1 = new Intent(notificationView.activity, CommunityDtailActivity.class);
                i1.putExtra("VIDEOID", datum.getLink().getId());
                notificationView.activity.startActivity(i1);
                break;
            case "news":
                Intent i2 = new Intent(notificationView.activity, NewsDtailActivity.class);
                i2.putExtra("VIDEOID", datum.getLink().getId());
                notificationView.activity.startActivity(i2);
                break;
            case "channel":
                Intent i3 = new Intent(notificationView.activity, ChannelDetailFragment.class);
                i3.putExtra("VIDEOID", datum.getLink().getId());
                notificationView.activity.startActivity(i3);
                break;
            case "video":
                Intent i4 = new Intent(notificationView.activity, VideoDtailActivity.class);
                i4.putExtra("VIDEOID", datum.getLink().getId());
                notificationView.activity.startActivity(i4);
                break;
        }
    }

    private void checkEventState(String eventid) {

        DisposableObserver<EventState> disposableObserver = new DisposableObserver<EventState>() {

            @Override
            public void onNext(EventState eventState) {

                if (eventState.getSuccess() && eventState.getData() != null) {
                    notificationView.startEventDetail(eventid, eventState.getData().getMode());
                } else {
                    notificationView.showMessage(eventState.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    notificationView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    notificationView.showMessage("Time Out");
                    checkEventState(eventid);

                } else if (e instanceof IOException) {
                    notificationView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    notificationView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
            }
        };


        notificationModel.getEventState(eventid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }


    private void sendNotificationSeen(String notificationid) {


        DisposableObserver<NotificationSeenResponse> disposableObserver = new DisposableObserver<NotificationSeenResponse>() {

            @Override
            public void onNext(NotificationSeenResponse notificationResponse) {
                if (notificationResponse.getSuccess()) {
//                    getNotificationList();

                } else {
                    if (!notificationResponse.getEmpty())
                        notificationView.showMessage(notificationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    notificationView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //notificationView.showMessage("Time Out");
                    sendNotificationSeen(notificationid);

                } else if (e instanceof IOException) {
                    notificationView.showMessage("Please check your network connection");

                } else {
                    // notificationView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        notificationModel.sendSeenstatus(notificationid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getNotificationList() {
        if (isFirst)
            notificationView.showLoadingDialog(true);

        DisposableObserver<NotificationResponse> disposableObserver = new DisposableObserver<NotificationResponse>() {

            @Override
            public void onNext(NotificationResponse notificationResponse) {
                if (notificationResponse.getSuccess() && notificationResponse.getData() != null) {
                    isFirst = false;
                    notificationList.clear();
                    notificationList.addAll(notificationResponse.getData());
                    notificationView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(notificationView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getNotificationList(page + 1);
                        }
                    });
                    notificationView.setNotificationList(notificationList);
                } else {
//                    if (!notificationResponse.getEmpty())
//                        notificationView.showMessage(notificationResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    notificationView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //notificationView.showMessage("Time Out");
                    getNotificationList();

                } else if (e instanceof IOException) {
                    notificationView.showMessage("Please check your network connection");

                } else {
                    // notificationView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                notificationView.showLoadingDialog(false);
            }
        };

        notificationModel.getNotificationlistObservable(page, notificationModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getNotificationList(int page) {

        DisposableObserver<NotificationResponse> disposableObserver = new DisposableObserver<NotificationResponse>() {

            @Override
            public void onNext(NotificationResponse exploreVideosResponse) {
               /* newsView.setNews(exploreNewsResponse);*/


                if (exploreVideosResponse.getSuccess()) {

                    if (exploreVideosResponse.getData() != null && exploreVideosResponse.getData().size() != 0) {
                        notificationList.addAll(exploreVideosResponse.getData());
                        notificationView.setNotificationList(notificationList);
                    }

                } else {

                    if (exploreVideosResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(notificationView.activity);
                    }
                    if (!exploreVideosResponse.getEmpty())
                        notificationView.showMessage(exploreVideosResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    notificationView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // notificationView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    notificationView.showMessage("Please check your network connection");

                } else {
                    notificationView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        notificationModel.getNotificationlistObservable(page, notificationModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
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


}
