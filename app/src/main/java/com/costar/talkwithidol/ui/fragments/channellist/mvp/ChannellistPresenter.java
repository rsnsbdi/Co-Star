package com.costar.talkwithidol.ui.fragments.channellist.mvp;


import com.costar.talkwithidol.app.network.models.exploreChannel.Datum;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
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

public class ChannellistPresenter {

    private final ChannellistView channellistView;
    private final ChannellistModel channellistModel;
    ArrayList<Datum> channelList = new ArrayList<>();
    int page = 0;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    public ChannellistPresenter(ChannellistView channellistView, ChannellistModel channellistModel) {
        this.channellistView = channellistView;
        this.channellistModel = channellistModel;

    }

    public void onCreateView() {

        getChannelList();

    }

    public  String getPreference(){
        return channellistModel.getData(Constants.CurrentFragment);
    }

    // get explore channel list
    private void getChannelList() {

        channellistView.showLoading(true);

        DisposableObserver<ExploreChannelResponse> disposableObserver = new DisposableObserver<ExploreChannelResponse>() {

            @Override
            public void onNext(ExploreChannelResponse exploreChannelResponse) {
                if (exploreChannelResponse.getSuccess() == true && exploreChannelResponse.getData() != null) {
                    channelList.addAll(exploreChannelResponse.getData());
                    channellistView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(channellistView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getChannelList(page);
                        }
                    });

                    channellistView.setChannel(channelList);
                } else {

                    if (exploreChannelResponse.getMessage().contains(Constants.AccessDenied)) {
                        channellistModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(channellistView.activity);
                    }
                    if (!exploreChannelResponse.getEmpty())
                        channellistView.showMessage(exploreChannelResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channellistView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    getChannelList();
                    // channellistView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channellistView.showMessage("Please check your network connection");

                } else {
                    channellistView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                channellistView.showLoading(false);
            }
        };

        channellistModel.getChannelObservable(page, channellistModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //load more
    private void getChannelList(int page) {

        DisposableObserver<ExploreChannelResponse> disposableObserver = new DisposableObserver<ExploreChannelResponse>() {

            @Override
            public void onNext(ExploreChannelResponse exploreChannelResponse) {
               /* newsView.setNews(exploreNewsResponse);*/


                if (exploreChannelResponse.getSuccess()) {

                    if (exploreChannelResponse.getData() != null && exploreChannelResponse.getData().size() != 0) {
                        channelList.addAll(exploreChannelResponse.getData());
                        channellistView.setChannel(channelList);
                    }

                } else {

                    if (exploreChannelResponse.getMessage().contains("403")) {
                        HomePageActivity.startLogout(channellistView.activity);
                    }
                    if (!exploreChannelResponse.getEmpty())
                        channellistView.showMessage(exploreChannelResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    channellistView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // channellistView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    channellistView.showMessage("Please check your network connection");

                } else {
                    channellistView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        channellistModel.getChannelObservable(page, channellistModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
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
