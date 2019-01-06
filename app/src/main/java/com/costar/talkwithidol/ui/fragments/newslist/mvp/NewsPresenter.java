package com.costar.talkwithidol.ui.fragments.newslist.mvp;


import com.costar.talkwithidol.ui.fragments.newslist.EndlessRecyclerViewScrollListener;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.app.network.models.exploreNews.ExploreNewsResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;

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

public class NewsPresenter {

    private static String newsId = null;
    private final NewsView newsView;
    private final NewsModel newsModel;
    int page = 0;
    ArrayList<DatumN> newsList = new ArrayList<>();
    DatumN datumN1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;


    public NewsPresenter(NewsView newsView, NewsModel newsModel) {
        this.newsView = newsView;
        this.newsModel = newsModel;

    }
    public  String getPreference(){
        return newsModel.getData(Constants.CurrentFragment);
    }

    public void onCreateView() {
        getNewsList();
        onDetailClick();
        onLikeClickedForId();
    }

    // get explore news list
    private void getNewsList() {
        newsView.showLoading(true);

        DisposableObserver<ExploreNewsResponse> disposableObserver = new DisposableObserver<ExploreNewsResponse>() {

            @Override
            public void onNext(ExploreNewsResponse exploreNewsResponse) {
               /* newsView.setNews(exploreNewsResponse);*/

                if (exploreNewsResponse.getSuccess() == true) {

                    newsList.addAll(exploreNewsResponse.getData());
                    newsView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(newsView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getNewsList(page);
                        }
                    });

                    newsView.setNewsList(newsList);

                } else {

                    if (exploreNewsResponse.getMessage().contains(Constants.AccessDenied)) {
                        newsModel.saveData(Constants.TOKEN, null);
                        HomePageActivity.startLogout(newsView.activity);
                    }
                    if (!exploreNewsResponse.getEmpty())
                        newsView.showMessage(exploreNewsResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //newsView.showMessage("Time Out");

                    getNewsList();
                } else if (e instanceof IOException) {
                    newsView.showMessage("Please check your network connection");

                } else {
                    newsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

                newsView.showLoading(false);
            }
        };

        newsModel.getNewsObservable(page, newsModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //load more
    private void getNewsList(int page) {

        DisposableObserver<ExploreNewsResponse> disposableObserver = new DisposableObserver<ExploreNewsResponse>() {

            @Override
            public void onNext(ExploreNewsResponse exploreNewsResponse) {
               /* newsView.setNews(exploreNewsResponse);*/


                if (exploreNewsResponse.getSuccess() == true) {

                    if (exploreNewsResponse.getData() != null && exploreNewsResponse.getData().size() != 0) {
                        newsList.addAll(exploreNewsResponse.getData());
                        newsView.setNewsList(newsList);
                    }

                } else {

                    if (exploreNewsResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(newsView.activity);
                    }
                    if (!exploreNewsResponse.getEmpty())
                        newsView.showMessage(exploreNewsResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // newsView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    newsView.showMessage("Please check your network connection");

                } else {
                    newsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        newsModel.getNewsObservable(page, newsModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    //like clicked
    private void LikeClicked() {

        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess() && likeEntityReponse.getData() != null) {
                    //newsView.showMessage(likeEntityReponse.getMessage());
//                    if (likeEntityReponse.getData().getStatus().equals("liked")) {
//                        newsView.onLikeSucess(datumN1);
//                    } else {
//                        newsView.onDisLikeSucess(datumN1);
//                    }


                } else {
                    newsView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    newsView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // newsView.showMessage("Time Out");
                    LikeClicked();

                } else if (e instanceof IOException) {
                    newsView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    newsView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
                newsView.showLoading(false);
            }
        };


        newsModel.getLikeEntittyObservable(likeEntityParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    //for detail view
    private void onDetailClick() {
        DisposableObserver<DatumN> disposableObserver = getDetailClickObserver();
        newsView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<DatumN> getDetailClickObserver() {
        return new DisposableObserver<DatumN>() {
            @Override
            public void onNext(DatumN datumN) {
                newsView.StartDetail(datumN.getNewsId());
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


    private void onLikeClickedForId() {
        DisposableObserver<DatumN> disposableObserver = getLikeClickObserver();
        newsView.getClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<DatumN> getLikeClickObserver() {
        return new DisposableObserver<DatumN>() {
            @Override
            public void onNext(DatumN datumN) {
//                    .startEventDetail(datumN.getNewsId());
                newsId = datumN.getNewsId();
                datumN1 = datumN;
                if (!datumN1.getLikes().getUserLike()) {
                    newsView.onLikeSucess(datumN1);
                } else {
                    newsView.onDisLikeSucess(datumN1);
                }
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


    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(newsId).type("node")
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
