package com.costar.talkwithidol.ui.fragments.home.mvp;


import android.view.View;

import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.ModifiedChannelList.ChannelList;
import com.costar.talkwithidol.app.network.models.ModifiedNewsList.NewsList;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.carousel.CarouselResponse;
import com.costar.talkwithidol.app.network.models.exploreChannel.Datum;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.app.network.models.exploreNews.DatumN;
import com.costar.talkwithidol.app.network.models.exploreNews.ExploreNewsResponse;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.app.network.models.promo.PromoResponse;
import com.costar.talkwithidol.app.network.models.talkidols.TalkIdolsResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import okhttp3.ResponseBody;

import static com.costar.talkwithidol.ui.activities.homepage.HomePageActivity.backPressed;
import static com.costar.talkwithidol.ui.fragments.home.HomeFragment.resume;

public class HomePresenter {
    private static String itemId = null;
    private static String itemType = null;
    private final HomeView homeView;
    private final HomeModel homeModel;
    //private FitnessUtils fitnessUtils;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    private List<com.costar.talkwithidol.app.network.models.talkidols.Datum> datumList  = new ArrayList <>();
    private int page =0;


    public HomePresenter(HomeView homeView, HomeModel homeModel) {
        this.homeView = homeView;
        this.homeModel = homeModel;
    }


    public void onCreateView() {


        RxBus.getInstance().toObservable().subscribe(o -> {

            if (o instanceof Integer) {

                if((Integer)o !=0){
                    try{
                        getTalkwithPagination( (Integer)o );
                    }catch (Exception e){
                        System.out.println("Report=="+e.getMessage());
                    }

            }}
        });

        backPressed = false;
        channelButtonObservable();
        eventButtonObservable();
        newsButtonObservable();
        videosButtonObservable();
        communityButtonObservable();

        getEventsList();
        getCarouselsList();
        getChannelList();
        getVideo();
        getPromote();
        getNewsList();
        getCommunityList();
        getTalkidol();
        homeView.videosPagerAdapter.getReceivedDrawData((id, type) -> {
            itemId = id;
            itemType = type;
            LikeClicked();

        });
        homeView.newsPagerAdapter.getReceivedDrawData((id, type) -> {
            itemId = id;
            itemType = type;
            LikeClicked();
        });
        homeView.communityPagerAdapter.getReceivedDrawData((id, type) -> {
            itemId = id;
            itemType = type;
            LikeClicked();
        });
        homeView.eventsPagerAdapter.getReceivedDrawData((id, type) -> {
            itemId = id;
            itemType = type;
            addToWatchList();
        });


    }

    private void addToWatchList() {

        DisposableObserver<AddToWatchlistResponse> disposableObserver = new DisposableObserver<AddToWatchlistResponse>() {

            @Override
            public void onNext(AddToWatchlistResponse addToWatchlistResponse) {

                if (addToWatchlistResponse.getSuccess() && addToWatchlistResponse.getData() != null) {
                    getEventsList();

                } else {
                    homeView.showMessage(addToWatchlistResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    //homeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    homeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
            }
        };


        homeModel.getAddToWatchListObservable(addToWatchlistParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    //initialize the parameters
    private AddToWatchlistParams addToWatchlistParams() {
        return AddToWatchlistParams.builder().id(itemId).type("node")
                .build();
    }

    // get explore community list
    private void getCommunityList() {

        DisposableObserver<ExploreCommunitylResponse> disposableObserver = new DisposableObserver<ExploreCommunitylResponse>() {

            @Override
            public void onNext(ExploreCommunitylResponse exploreCommunitylResponse) {
                if (exploreCommunitylResponse.getSuccess() && exploreCommunitylResponse.getData() != null) {
                    homeView.setCommunity(exploreCommunitylResponse);
                } else {
                    homeView.showMessage(exploreCommunitylResponse.getMessage());
                    homeView.ultraViewPager_community.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getCommunityList();

                } else if (e instanceof IOException) {
                    //homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getCommentObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    // get explore talkidol list
    private void getTalkidol() {
        homeView.showProgressDialog(true);
        resume = false;
        DisposableObserver<TalkIdolsResponse> disposableCarouselObserver = new DisposableObserver<TalkIdolsResponse>() {

            @Override
            public void onNext(TalkIdolsResponse talkIdolsResponse) {
                if (talkIdolsResponse.getSuccess() && talkIdolsResponse.getData() != null) {


                    datumList = talkIdolsResponse.getData();
                    homeView.setTalkIdol(datumList);
                    if(talkIdolsResponse.getData().size()>9){

                        page = page+1;
                        getTalkwithPagination(page);
                    }else{
                        homeView.setTalkIdol(datumList);
                    }

                } else {
                    homeView.showMessage(talkIdolsResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getTalkidol();

                } else if (e instanceof IOException) {
                    //homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());

                }

            }

            @Override
            public void onComplete() {
                homeView.showProgressDialog(false);
            }
        };

        homeModel.getTalkidolObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableCarouselObserver);
        compositeDisposable.add(disposableCarouselObserver);
    }


    // get explore promote list
    private void getPromote() {

        DisposableObserver<PromoResponse> disposableObserver = new DisposableObserver<PromoResponse>() {

            @Override
            public void onNext(PromoResponse promoResponse) {
                if (promoResponse.getSuccess() && promoResponse.getData() != null) {
                    homeView.setPromo(promoResponse);
                } else {
                    homeView.showMessage("PromoResponse:" + promoResponse.getMessage());
                    homeView.ultra_viewpager_promo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getPromote();

                } else if (e instanceof IOException) {
                    //homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getPromoObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore video list
    private void getVideo() {

        DisposableObserver<ExploreVideosResponse> disposableObserver = new DisposableObserver<ExploreVideosResponse>() {

            @Override
            public void onNext(ExploreVideosResponse exploreVideosResponse) {
                if (exploreVideosResponse.getSuccess() && exploreVideosResponse.getData() != null) {
                    homeView.setVideo(exploreVideosResponse);
                } else {
                    homeView.showMessage(exploreVideosResponse.getMessage());
                    homeView.ultraViewPager_videos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //homeView.showMessage("Time Out");
                    getVideo();

                } else if (e instanceof IOException) {
                    // homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getVideoObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore channel list
    private void getChannelList() {

        DisposableObserver<ExploreChannelResponse> disposableObserver = new DisposableObserver<ExploreChannelResponse>() {

            @Override
            public void onNext(ExploreChannelResponse exploreChannelResponse) {
                if (exploreChannelResponse.getSuccess() && exploreChannelResponse.getData() != null) {

                    ArrayList<Datum> datum = new ArrayList<>();
                    datum.addAll(exploreChannelResponse.getData());
                    if (exploreChannelResponse.getData().size() % 2 != 0) {
                        datum.add(exploreChannelResponse.getData().get(0));
                    }


                    ArrayList<ChannelList> channelListTwoItem = new ArrayList<>();

                    final ChannelList channelList0 = new ChannelList();
                    Datum datum0 = datum.get(0);
                    Datum datum1 = datum.get(1);
                    channelList0.data = datum0;
                    channelList0.data1 = datum1;


                    final ChannelList channelList1 = new ChannelList();
                    Datum datum2 = datum.get(2);
                    Datum datum3 = datum.get(3);
                    channelList1.data = datum2;
                    channelList1.data1 = datum3;

                    final ChannelList channelList2 = new ChannelList();
                    Datum datum4 = datum.get(4);
                    Datum datum5 = datum.get(5);
                    channelList2.data = datum4;
                    channelList2.data1 = datum5;

                    channelListTwoItem.add(0, channelList0);
                    channelListTwoItem.add(1, channelList1);
                    channelListTwoItem.add(2, channelList2);


                    homeView.setChannel(channelListTwoItem);


                } else {
                    homeView.showMessage(exploreChannelResponse.getMessage());
                    homeView.ultraViewPager_channel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getChannelList();

                } else if (e instanceof IOException) {
                    //homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getChannelObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore news list
    private void getNewsList() {

        DisposableObserver<ExploreNewsResponse> disposableObserver = new DisposableObserver<ExploreNewsResponse>() {

            @Override
            public void onNext(ExploreNewsResponse exploreNewsResponse) {

                if (exploreNewsResponse.getSuccess() && exploreNewsResponse.getData() != null) {

                    ArrayList<DatumN> datum = new ArrayList<>();
                    datum.addAll(exploreNewsResponse.getData());
                    ArrayList<NewsList> newsListItem = new ArrayList<>();

                    final NewsList newsList0 = new NewsList();
                    DatumN datum0 = datum.get(0);
                    DatumN datum1 = datum.get(1);
                    newsList0.data = datum0;
                    newsList0.data1 = datum1;


                    final NewsList newsList1 = new NewsList();
                    DatumN datum2 = datum.get(2);
                    DatumN datum3 = datum.get(3);
                    newsList1.data = datum2;
                    newsList1.data1 = datum3;

                    final NewsList newsList2 = new NewsList();
                    DatumN datum4 = datum.get(4);
                    DatumN datum5 = datum.get(5);
                    newsList2.data = datum4;
                    newsList2.data1 = datum5;

                    newsListItem.add(0, newsList0);
                    newsListItem.add(1, newsList1);
                    newsListItem.add(2, newsList2);


                    homeView.setNews(newsListItem);


                } else {
                    homeView.showMessage(exploreNewsResponse.getMessage());
                    homeView.ultraViewPager_news.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getNewsList();

                } else if (e instanceof IOException) {
                    // homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getNewsObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore event list
    private void getEventsList() {

        DisposableObserver<ExploreEventResponse> disposableObserver = new DisposableObserver<ExploreEventResponse>() {

            @Override
            public void onNext(ExploreEventResponse exploreEventResponse) {
                if (exploreEventResponse.getSuccess() && exploreEventResponse.getData() != null) {
                    homeView.setEvent(exploreEventResponse);
                } else {
                    homeView.showMessage(exploreEventResponse.getMessage());
                    homeView.ultraViewPager_event.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    getEventsList();

                } else if (e instanceof IOException) {
                    // homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getEventsObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    // get explore carousel list
    private void getCarouselsList() {

        DisposableObserver<CarouselResponse> disposableCarouselObserver = new DisposableObserver<CarouselResponse>() {

            @Override
            public void onNext(CarouselResponse carouselResponse) {

                if (carouselResponse.getSuccess() && carouselResponse.getData() != null) {
                    homeView.setCarousel(carouselResponse);
                } else {
                    if (carouselResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(homeView.activity);
                    }
                    homeView.showMessage(carouselResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //homeView.showMessage("Time Out");
                    getCarouselsList();

                } else if (e instanceof IOException) {
                    homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());

                }
                homeView.showProgressDialog(false);
            }

            @Override
            public void onComplete() {

            }
        };

        homeModel.getCarouselObservable(homeModel.getData(Constants.COOKIE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableCarouselObserver);
        compositeDisposable.add(disposableCarouselObserver);
    }


    private void LikeClicked() {

        DisposableObserver<LikeEntityReponse> disposableObserver = new DisposableObserver<LikeEntityReponse>() {

            @Override
            public void onNext(LikeEntityReponse likeEntityReponse) {

                if (likeEntityReponse.getSuccess() && likeEntityReponse.getData() != null) {
                    //newsView.showMessage(likeEntityReponse.getMessage());

                    if (itemType.equals("video")) {
                        getVideo();
                    } else if (itemType.equals("news")) {
                        //getNewsList();
                    } else if (itemType.equals("community")) {
                        getCommunityList();
                    }

                } else {
                    homeView.showMessage(likeEntityReponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));
                    //loginModel.startDashboard();
                } else if (e instanceof SocketTimeoutException) {
                    // homeView.showMessage("Time Out");
                    LikeClicked();

                } else if (e instanceof IOException) {
//                    homeView.showMessage("Please check your network connection");
                    // loginModel.startDashboard();

                } else {
                    //todo changes
                    homeView.showMessage(e.getMessage());
                    // loginModel.startDashboard();

                }

            }

            @Override
            public void onComplete() {
//                homeView.showLoading(false);
            }
        };


        homeModel.getLikeEntittyObservable(likeEntityParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    //initialize the parameters
    private LikeEntityParams likeEntityParams() {
        return LikeEntityParams.builder().id(itemId).type("node")
                .build();
    }


    public void onDestroyView() {
        compositeDisposable.clear();
    }

    private void channelButtonObservable() {
        homeView.channelObservable()
                .subscribe(o -> loadBus("channelListH"));
    }

    private void eventButtonObservable() {
        homeView.eventObservable()
                .subscribe(o -> loadBus("eventListH"));
    }

    private void videosButtonObservable() {
        homeView.videosObservable()
                .subscribe(o -> loadBus("videoListH"));
    }

    private void newsButtonObservable() {
        homeView.newsObservable()
                .subscribe(o -> loadBus("newsListH"));
    }

    private void communityButtonObservable() {
        homeView.communityObservable()
                .subscribe(o -> loadBus("communityListH"));
    }

    private void loadBus(String name) {

        try {
            RxBus.getInstance().send(name);
        } catch (Exception e) {

        }

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
    public void getTalkwithPagination(int page) {

        DisposableObserver<TalkIdolsResponse> disposableObservers = new DisposableObserver<TalkIdolsResponse>() {

            @Override
            public void onNext(TalkIdolsResponse talkWithIdolResponse) {

                if (talkWithIdolResponse.getSuccess() && talkWithIdolResponse.getData() != null) {

                    datumList.addAll(talkWithIdolResponse.getData());
                    homeView.setTalkIdol(datumList);
//

                } else {
                    if (talkWithIdolResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(homeView.activity);
                    }
                    homeView.showMessage(talkWithIdolResponse.getMessage());
                }
            }


            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    homeView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    homeView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    homeView.showMessage("Please check your network connection");

                } else {
                    homeView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
            homeModel.getTalkList(page, homeModel.getData(Constants.COOKIE ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(disposableObservers);
                   compositeDisposable.add(disposableObservers);


    }
//
}
