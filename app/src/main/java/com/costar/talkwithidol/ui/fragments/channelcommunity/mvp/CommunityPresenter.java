package com.costar.talkwithidol.ui.fragments.channelcommunity.mvp;


import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.DatumC;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
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

public class CommunityPresenter {
    private static String forumId = null;

    private final CommunityView communityView;
    private final CommunityModel communityModel;
    //private FitnessUtils fitnessUtils;
    ArrayList<DatumC> communityList = new ArrayList<>();
    int page = 0;
    DatumC datum1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;

    public CommunityPresenter(CommunityView communityView, CommunityModel communityModel) {
        this.communityView = communityView;
        this.communityModel = communityModel;

    }

    public void onCreateView() {

        getChannelCommunityList();
        onDetailClick();
        onLikeClickedForId();
    }

    // get explore channel list
    private void getChannelCommunityList() {
        communityView.showLoading(true);
        DisposableObserver<ExploreCommunitylResponse> disposableObserver = new DisposableObserver<ExploreCommunitylResponse>() {

            @Override
            public void onNext(ExploreCommunitylResponse exploreCommunitylResponse) {
                if (exploreCommunitylResponse.getSuccess() && exploreCommunitylResponse.getData() != null) {
                    communityList.addAll(exploreCommunitylResponse.getData());
                    communityView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(communityView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getCommunityList(page);
                        }
                    });


                    communityView.setCommunity(communityList);
                } else {
                    communityView.showNoContent(true, exploreCommunitylResponse.getMessage());
//                        communityView.showMessage(exploreCommunitylResponse.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityView.showMessage("Time Out");
                    getChannelCommunityList();

                } else if (e instanceof IOException) {
                    communityView.showMessage("Please check your network connection");

                } else {
                    communityView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                communityView.showLoading(false);

            }
        };

        communityModel.getchannelCommunityObservable(page, ChannelDetailFragment.videoId, communityModel.getData(Constants.COOKIE))
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getCommunityList(int page) {

        DisposableObserver<ExploreCommunitylResponse> disposableObserver = new DisposableObserver<ExploreCommunitylResponse>() {

            @Override
            public void onNext(ExploreCommunitylResponse exploreCommunitylResponse) {

                if (exploreCommunitylResponse.getSuccess()) {

                    if (exploreCommunitylResponse.getData() != null && exploreCommunitylResponse.getData().size() != 0) {
                        communityList.addAll(exploreCommunitylResponse.getData());
                        communityView.setCommunity(communityList);
                    }

                } else {

                    if (exploreCommunitylResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(communityView.activity);
                    }

                    if (!exploreCommunitylResponse.getEmpty())
                        communityView.showMessage(exploreCommunitylResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityView.showMessage("Please check your network connection");

                } else {
                    communityView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        communityModel.getchannelCommunityObservable(page, ChannelDetailFragment.videoId, communityModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private void onDetailClick() {
        DisposableObserver<DatumC> disposableObserver = getDetailClickObserver();
        communityView.getDetailClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    private DisposableObserver<DatumC> getDetailClickObserver() {
        return new DisposableObserver<DatumC>() {
            @Override
            public void onNext(DatumC datum) {
                communityView.StartDetail(datum.getForumId());
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
        DisposableObserver<DatumC> disposableObserver = getLikeClickObserver();
        communityView.getClickObservable().subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<DatumC> getLikeClickObserver() {
        return new DisposableObserver<DatumC>() {
            @Override
            public void onNext(DatumC datum) {
//                    .startEventDetail(datum.getNewsId());
                forumId = datum.getForumId();
                datum1 = datum;
                if (!datum1.getLikes().getUserLike()) {
                    communityView.onLikeSucess(datum1);
                } else {
                    communityView.onDisLikeSucess(datum1);
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
                   /* communityView.showMessage(likeEntityReponse.getMessage());
                    communityView.mAdapter.notifyDataSetChanged();
                    getChannelCommunityList();*/

//                    if (likeEntityReponse.getData().getStatus().equals("liked")) {
//                        communityView.onLikeSucess(datum1);
//                    } else {
//                        communityView.onDisLikeSucess(datum1);
//                    }

                    if (likeEntityReponse.getData().getStatus().equals("liked")) {
                        communityView.onLikeSucess(datum1, likeEntityReponse);
                    } else {
                        communityView.onDisLikeSucess(datum1, likeEntityReponse);
                    }


                } else {
                    communityView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    communityView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    communityView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    communityView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    communityView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {

            }
        };


        communityModel.getLikeEntittyObservable(likeEntityParams())
                // .doOnNext(__ ->eventView.showLoading(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnEach(__ -> eventView.showLoading(false))
                .subscribe(disposableObserver);
    }


    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(forumId).type("node")
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
