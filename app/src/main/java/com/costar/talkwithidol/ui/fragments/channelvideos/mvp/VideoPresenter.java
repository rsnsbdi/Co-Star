package com.costar.talkwithidol.ui.fragments.channelvideos.mvp;


import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreVideos.DatumV;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
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

public class VideoPresenter {
    private static String videoId = null;
    private final VideoView videoView;
    private final VideoModel videoModel;
    ArrayList<DatumV> videoList = new ArrayList<>();
    int page = 0;
    DatumV datum1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public VideoPresenter(VideoView videoView, VideoModel videoModel) {
        this.videoView = videoView;
        this.videoModel = videoModel;

    }


    public void onCreate() {
        getChannelVideo();
        onDetailClick();
        onLikeClickedForId();
    }

    // get explore video list
    private void getChannelVideo() {
        videoView.showLoading(true);
        DisposableObserver<ExploreVideosResponse> disposableObserver = new DisposableObserver<ExploreVideosResponse>() {

            @Override
            public void onNext(ExploreVideosResponse exploreVideosResponse) {
                if (exploreVideosResponse.getSuccess() && exploreVideosResponse.getData() != null) {
                    videoList.addAll(exploreVideosResponse.getData());
                    videoView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(videoView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getChannelVideo(page);
                        }
                    });
                    videoView.setVideo(videoList);
                } else {
                        videoView.showNoContent(true, exploreVideosResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoView.showMessage("Time Out");
                    getChannelVideo();

                } else if (e instanceof IOException) {
                    videoView.showMessage("Please check your network connection");

                } else {
                    videoView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                videoView.showLoading(false);

            }
        };

        videoModel.getChannelVideoObservable(page, ChannelDetailFragment.videoId, videoModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    //load more
    private void getChannelVideo(int page) {

        DisposableObserver<ExploreVideosResponse> disposableObserver = new DisposableObserver<ExploreVideosResponse>() {

            @Override
            public void onNext(ExploreVideosResponse exploreVideosResponse) {
                if (exploreVideosResponse.getSuccess()) {

                    if (exploreVideosResponse.getData() != null && exploreVideosResponse.getData().size() != 0) {
                        videoList.addAll(exploreVideosResponse.getData());
                        videoView.setVideo(videoList);
                    }

                } else {

                    if (exploreVideosResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(videoView.activity);
                    }
                    if (!exploreVideosResponse.getEmpty())
                        videoView.showMessage(exploreVideosResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // videoView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoView.showMessage("Please check your network connection");

                } else {
                    videoView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        videoModel.getChannelVideoObservable(page, ChannelDetailFragment.videoId, videoModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    //for detail view
    private void onDetailClick() {
        DisposableObserver<DatumV> disposableObserver = getDetailClickObserver();
        videoView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<DatumV> getDetailClickObserver() {
        return new DisposableObserver<DatumV>() {
            @Override
            public void onNext(DatumV datum) {
                videoView.StartDetail(datum.getVideoId());
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


    private void onLikeClickedForId() {
        DisposableObserver<DatumV> disposableObserver = getLikeClickObserver();
        videoView.getClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<DatumV> getLikeClickObserver() {
        return new DisposableObserver<DatumV>() {
            @Override
            public void onNext(DatumV datum) {
//                    .startEventDetail(datum.getNewsId());
                videoId = datum.getVideoId();
                datum1 = datum;

                if (!datum1.getLikes().getUserLike()) {
                    videoView.onLikeSucess(datum1);
                } else {
                    videoView.onDisLikeSucess(datum1);
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


    //like clicked
    private void LikeClicked() {

        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess()) {

//                    if (likeEntityReponse.getData().getStatus().equals("liked")) {
//                        videoView.onLikeSucess(datum1);
//                    } else {
//                        videoView.onDisLikeSucess(datum1);
//                    }


                } else {
                    videoView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    videoView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    //videoView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    videoView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    videoView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };


        videoModel.getLikeEntittyObservable(likeEntityParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(videoId).type("node")
                .build();
    }

    public void onDestroyView() {
        compositeDisposable.clear();
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
