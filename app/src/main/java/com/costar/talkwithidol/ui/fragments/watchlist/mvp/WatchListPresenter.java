package com.costar.talkwithidol.ui.fragments.watchlist.mvp;


import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class WatchListPresenter {
    private static String eventId = null;
    private final WatchListView watchListView;
    private final WatchListModel watchListModel;
    //private FitnessUtils fitnessUtils;
    DatumE datumE1;
    ArrayList<DatumE> watchlist = new ArrayList<>();
    int page = 0;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WatchListPresenter(WatchListView watchListView, WatchListModel watchListModel) {
        this.watchListView = watchListView;
        this.watchListModel = watchListModel;
    }

    public  String getPreference(){
        return watchListModel.getData(Constants.CurrentFragment);
    }

    public void onCreateView() {

        getEventsList();
        onDetailClick();
        addToWatchListClickedForid();

    }

    // get explore event list
    private void getEventsList() {

        watchListView.showLoading(true);

        DisposableObserver<ExploreEventResponse> disposableObserver = new DisposableObserver<ExploreEventResponse>() {

            @Override
            public void onNext(ExploreEventResponse exploreEventResponse) {
                if (exploreEventResponse.getSuccess() && exploreEventResponse.getData() != null) {
                    watchlist.addAll(exploreEventResponse.getData());
                    watchListView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(watchListView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getEventsList(page );
                        }
                    });


                    watchListView.setEvent(watchlist);
                } else {
                    if (exploreEventResponse.getMessage().contains(Constants.AccessDenied)) {
                        watchListModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(watchListView.activity);
                    }

                    if (!exploreEventResponse.getEmpty())
                        watchListView.showMessage(exploreEventResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    watchListView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // watchListView.showMessage("Time Out");
                    getEventsList();

                } else if (e instanceof IOException) {
                    watchListView.showMessage("Please check your network connection");

                } else {
                    watchListView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                watchListView.showLoading(false);
            }
        };

        watchListModel.getWatchListObservable(page, watchListModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getEventsList(int page) {

        DisposableObserver<ExploreEventResponse> disposableObserver = new DisposableObserver<ExploreEventResponse>() {

            @Override
            public void onNext(ExploreEventResponse exploreEventResponse) {
                if (exploreEventResponse.getSuccess() && exploreEventResponse.getData() != null) {

                    if (exploreEventResponse.getData() != null && exploreEventResponse.getData().size() != 0) {
                        watchlist.addAll(exploreEventResponse.getData());
                        watchListView.setEvent(watchlist);
                    }
                } else {
                    if (exploreEventResponse.getMessage().contains("403")) {
                        HomePageActivity.startLogout(watchListView.activity);
                    }
                    if (!exploreEventResponse.getEmpty())
                        watchListView.showMessage(exploreEventResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    watchListView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                   // watchListView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    watchListView.showMessage("Please check your network connection");

                } else {
                    watchListView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                watchListView.showLoading(false);
            }
        };

        watchListModel.getWatchListObservable(page, watchListModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    //for detail view
    private void onDetailClick() {
        DisposableObserver<DatumE> disposableObserver = getDetailClickObserver();
        watchListView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<DatumE> getDetailClickObserver() {
        return new DisposableObserver<DatumE>() {
            @Override
            public void onNext(DatumE datumE) {
                watchListView.StartDetail(datumE.getEventId(), datumE.getMode());
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


    private void addToWatchListClickedForid() {
        DisposableObserver<DatumE> disposableObserver = getAddToWatchListClickObserver();
        watchListView.getClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<DatumE> getAddToWatchListClickObserver() {
        return new DisposableObserver<DatumE>() {
            @Override
            public void onNext(DatumE datumE) {
//                    .startEventDetail(datumE.getNewsId());
                eventId = datumE.getEventId();
                datumE1 = datumE;


                removeFromWatchlist();
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
    private void removeFromWatchlist() {

        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse addToWatchlistResponse) {

                if (addToWatchlistResponse.getSuccess() && addToWatchlistResponse.getData() != null) {
                    if (addToWatchlistResponse.getData().getStatus().equals("removed"))
                        watchListView.onLikeSucess(datumE1);
                } else {
                    watchListView.showMessage(addToWatchlistResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    watchListView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                   // watchListView.showMessage("Time Out");
                    removeFromWatchlist();

                } else if (e instanceof IOException) {
                    watchListView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    watchListView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                watchListView.showLoading(false);
            }
        };


        watchListModel.removeFromWatchlistObservable(addToWatchlistParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
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
