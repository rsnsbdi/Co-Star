package com.costar.talkwithidol.ui.fragments.eventlist.mvp;


import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.exploreEvent.DatumE;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
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

public class EventPresenter {

    private static String eventId = null;
    private final EventView eventView;
    private final EventModel eventModel;
    //private FitnessUtils fitnessUtils;
    ArrayList<DatumE> eventList = new ArrayList<>();
    DatumE datumE1;
    //private FitnessUtils fitnessUtils;
    int page = 0;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;

    public EventPresenter(EventView eventView, EventModel eventModel) {
        this.eventView = eventView;
        this.eventModel = eventModel;
    }


    public void onCreate() {

        getEventsList();
        onDetailClick();
        addToWatchListClickedForid();
    }

    public  String getPreference(){
        return eventModel.getData(Constants.CurrentFragment);
    }

    // get explore event list
    private void getEventsList() {

        eventView.showLoading(true);

        DisposableObserver<ExploreEventResponse> disposableObserver = new DisposableObserver<ExploreEventResponse>() {

            @Override
            public void onNext(ExploreEventResponse exploreEventResponse) {
                if (exploreEventResponse.getSuccess() && exploreEventResponse.getData() != null) {
                    eventList.addAll(exploreEventResponse.getData());
                    eventView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(eventView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getEventList(page);
                        }
                    });


                    eventView.setEvent(eventList);
                } else {
                    if (exploreEventResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(eventView.activity);
                    }
                    if (!exploreEventResponse.getEmpty())
                        eventView.showMessage(exploreEventResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventView.showMessage("Please check your network connection");

                } else {
                    eventView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                eventView.showLoading(false);
            }
        };

        eventModel.getEventsObservable(page, eventModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getEventList(int page) {

        DisposableObserver<ExploreEventResponse> disposableObserver = new DisposableObserver<ExploreEventResponse>() {

            @Override
            public void onNext(ExploreEventResponse exploreEventResponse) {

                if (exploreEventResponse.getSuccess() && exploreEventResponse.getData() != null) {

                    if (exploreEventResponse.getData() != null && exploreEventResponse.getData().size() != 0) {
                        eventList.addAll(exploreEventResponse.getData());
                        eventView.setEvent(eventList);
                    }

                } else {


                    if (exploreEventResponse.getMessage().contains(Constants.AccessDenied)) {
                        eventModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(eventView.activity);
                    }
                    if (!exploreEventResponse.getEmpty())
                        eventView.showMessage(exploreEventResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    eventView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventView.showMessage("Please check your network connection");

                } else {
                    eventView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                eventView.showLoading(false);
            }
        };

        eventModel.getEventsObservable(page, eventModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    //for detail view
    private void onDetailClick() {
        DisposableObserver<DatumE> disposableObserver = getDetailClickObserver();
        eventView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<DatumE> getDetailClickObserver() {
        return new DisposableObserver<DatumE>() {
            @Override
            public void onNext(DatumE datumE) {
                checkEventState(datumE.getEventId());
//                eventView.startEventDetail(datumE.getEventId(), datumE.getMode());
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


    private void checkEventState(String eventid) {

        DisposableObserver<EventState> disposableObserver = new DisposableObserver<EventState>() {

            @Override
            public void onNext(EventState eventState) {

                if (eventState.getSuccess() && eventState.getData() != null) {
                    eventView.StartDetail(eventid, eventState.getData().getMode());
                } else {
                    eventView.showMessage(eventState.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                eventView.showLoading(false);
            }
        };


        eventModel.getEventState(eventid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }


    private void addToWatchListClickedForid() {
        DisposableObserver<DatumE> disposableObserver = getAddToWatchListClickObserver();
        eventView.getClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<DatumE> getAddToWatchListClickObserver() {
        return new DisposableObserver<DatumE>() {
            @Override
            public void onNext(DatumE datumE) {
//                    .startEventDetail(datumE.getNewsId());
                eventId = datumE.getEventId();
                datumE1 = datumE;

                if (!datumE1.getUserWatchlist()) {
                    eventView.onLikeSucess(datumE1);
                } else {
                    eventView.onDisLikeSucess(datumE1);
                }
                addToWatchList();
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


    //like clicked
    private void addToWatchList() {

        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse addToWatchlistResponse) {

                if (addToWatchlistResponse.getSuccess() && addToWatchlistResponse.getData() != null) {
//                    if (addToWatchlistResponse.getData().getStatus().equals("added")) {
//                        eventView.onLikeSucess(datumE1);
//                    } else {
//                        eventView.onDisLikeSucess(datumE1);
//                    }

                } else {
                    eventView.showMessage(addToWatchlistResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    eventView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    eventView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    eventView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    eventView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                eventView.showLoading(false);
            }
        };


        eventModel.getAddToWatchListObservable(addToWatchlistParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }


    //initialize the parameters
    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(eventId).type("node")
                .build();
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
