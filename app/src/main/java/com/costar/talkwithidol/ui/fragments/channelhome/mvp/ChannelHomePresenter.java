package com.costar.talkwithidol.ui.fragments.channelhome.mvp;


import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.channelhome.ChannelHomeResponse;
import com.costar.talkwithidol.app.network.models.channelhome.Datum;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;
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

public class ChannelHomePresenter {

    public static String id = null;
    private final ChannelHomeView channelHomeView;
    private final ChannelHomeModel channelHomeModel;
    ChannelHomeResponse channelHomeResponse1;
    ArrayList<Datum> channelHomeList = new ArrayList<>();
    int page = 0;
    Datum datumC;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ChannelHomePresenter(ChannelHomeView channelHomeView, ChannelHomeModel channelHomeModel) {
        this.channelHomeView = channelHomeView;
        this.channelHomeModel = channelHomeModel;

    }


    public void onCreateView() {
        getChannelHomeList();
        onDetailClick();
        onDetailClickE();
        onDetailClickF();
        onDetailClickN();

        onLikeClickedForId();
        onLikeClickedFForId();
        onLikeClickedNForId();
        onLikeClickedEForId();

    }
    private void checkEventState(String eventid) {

        DisposableObserver<EventState> disposableObserver = new DisposableObserver<EventState>() {

            @Override
            public void onNext(EventState eventState) {

                if (eventState.getSuccess() && eventState.getData() != null) {
                    channelHomeView.StartDetail(eventid, eventState.getData().getMode());
                } else {
                    channelHomeView.showMessage(eventState.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    channelHomeView.showMessage("Time Out");
                    checkEventState(eventid);

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelHomeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                channelHomeView.showLoading(false);
            }
        };


        channelHomeModel.getEventState(eventid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }
    private void getChannelHomeList() {
        channelHomeView.showLoading(true);
        DisposableObserver<ChannelHomeResponse> disposableObserver = new DisposableObserver<ChannelHomeResponse>() {

            @Override
            public void onNext(ChannelHomeResponse channelHomeResponse) {


                if (channelHomeResponse.getSuccess() && channelHomeResponse.getData() != null) {
                    channelHomeList.addAll(channelHomeResponse.getData());
                    channelHomeView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(channelHomeView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getChannelHomeList(page);
                        }
                    });


                    channelHomeView.setChannelHome(channelHomeList);
                } else {
//                        channelHomeView.showMessage(channelHomeResponse.getMessage());
                        channelHomeView.showNoContent(true, channelHomeResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelHomeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");

                } else {
                    channelHomeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channelHomeView.showLoading(false);
            }
        };

        channelHomeModel.getChannelHomeObservable(page, ChannelDetailFragment.videoId, channelHomeModel.getData(Constants.COOKIE))
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))s
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void getChannelHomeList(int page) {

        DisposableObserver<ChannelHomeResponse> disposableObserver = new DisposableObserver<ChannelHomeResponse>() {

            @Override
            public void onNext(ChannelHomeResponse channelHomeResponse) {


                if (channelHomeResponse.getSuccess() && channelHomeResponse.getData() != null) {
                    channelHomeList.addAll(channelHomeResponse.getData());
                    channelHomeView.setChannelHome(channelHomeList);

                } else {
                    if (!channelHomeResponse.getEmpty())
                        channelHomeView.showMessage(channelHomeResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channelHomeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");

                } else {
                    channelHomeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        channelHomeModel.getChannelHomeObservable(page, ChannelDetailFragment.videoId, channelHomeModel.getData(Constants.COOKIE))
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))s
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    //like clicked
    private void LikeClicked() {

        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getData().getStatus().equals("liked")) {
                    channelHomeView.onLikeSucess(datumC);
                } else {
                    channelHomeView.onDisLikeSucess(datumC);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // channelHomeView.showMessage("Time Out");
                    LikeClicked();

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelHomeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }
            }

            @Override
            public void onComplete() {

            }
        };

        channelHomeModel.getLikeEntittyObservable(likeEntityParams())
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))
                .subscribe(disposableObserver);
    }

    //like clicked
    private void LikeClickedCommunity() {


        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess()) {
                   /* communityView.showMessage(likeEntityReponse.getMessage());
                    getCommunityList();*/

                    if (likeEntityReponse.getData().getStatus().equals("liked")) {
                        channelHomeView.onLikeSucessCommunity(datumC, likeEntityReponse);
                    } else {
                        channelHomeView.onDisLikeSucessCommunity(datumC, likeEntityReponse);
                    }

                } else {
                    channelHomeView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    //channelHomeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelHomeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();
                }

            }

            @Override
            public void onComplete() {

            }
        };

        channelHomeModel.getLikeEntittyObservable(likeEntityParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //like clicked
    private void addToWatchlist() {

        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse addToWatchlistResponse) {

                if (addToWatchlistResponse.getSuccess() && addToWatchlistResponse.getData() != null) {
                    if (addToWatchlistResponse.getData().getStatus().equals("added")) {
                        channelHomeView.onLikeSucessE(datumC);
                    } else {
                        channelHomeView.onDisLikeSucessE(datumC);
                    }

                } else {
                    channelHomeView.showMessage(addToWatchlistResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channelHomeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    //channelHomeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channelHomeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    channelHomeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };


        channelHomeModel.getAddToWatchListObservable(addToWatchlistParams())
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))
                .subscribe(disposableObserver);
    }


    //for detail view
    private void onDetailClick() {
        DisposableObserver<Datum> disposableObserver = getDetailClickObserver();
        channelHomeView.getDetailClickVkObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getDetailClickObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {
                channelHomeView.StartDetail(channelHomeResponse1, datum);
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
    //close

    //like for videos
    private void onLikeClickedForId() {
        DisposableObserver<Datum> disposableObserver = getLikeClickObserver();
        channelHomeView.getClickVObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getLikeClickObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {

                id = datum.getVideoId();
                datumC = datum;
                LikeClicked();
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
    //close


    //for detail view
    private void onDetailClickE() {
        DisposableObserver<Datum> disposableObserver = getDetailClickEObserver();
        channelHomeView.getDetailClickEObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getDetailClickEObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {
//                channelHomeView.startEventDetail(channelHomeResponse1, datum);
                checkEventState(datum.getEventId());
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
    //close

    //like
    private void onLikeClickedEForId() {
        DisposableObserver<Datum> disposableObserver = getLikeClickEObserver();
        channelHomeView.getClickEObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getLikeClickEObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {

                id = datum.getEventId();
                datumC = datum;
                addToWatchlist();

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
    //close


    //for detail view
    private void onDetailClickF() {
        DisposableObserver<Datum> disposableObserver = getDetailClickFObserver();
        channelHomeView.getDetailClickFObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getDetailClickFObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {
                channelHomeView.StartDetail(channelHomeResponse1, datum);
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
    //close

    //like
    private void onLikeClickedFForId() {
        DisposableObserver<Datum> disposableObserver = getLikeClickFObserver();
        channelHomeView.getClickFObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getLikeClickFObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {

                id = datum.getForumId();
                datumC = datum;
                LikeClickedCommunity();

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
    //close


    //for detail view
    private void onDetailClickN() {
        DisposableObserver<Datum> disposableObserver = getDetailClickNObserver();
        channelHomeView.getDetailClickNObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getDetailClickNObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {
                channelHomeView.StartDetail(channelHomeResponse1, datum);
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
    //close

    //like
    private void onLikeClickedNForId() {
        DisposableObserver<Datum> disposableObserver = getLikeClickNObserver();
        channelHomeView.getClickNObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<Datum> getLikeClickNObserver() {
        return new DisposableObserver<Datum>() {
            @Override
            public void onNext(Datum datum) {

                id = datum.getNewsId();
                datumC = datum;
                LikeClicked();

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
    //close


    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(id).type("node")
                .build();
    }

    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(id).type("node")
                .build();
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
