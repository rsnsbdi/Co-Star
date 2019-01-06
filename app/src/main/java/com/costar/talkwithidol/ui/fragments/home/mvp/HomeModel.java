package com.costar.talkwithidol.ui.fragments.home.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.carousel.CarouselResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.app.network.models.exploreNews.ExploreNewsResponse;
import com.costar.talkwithidol.app.network.models.exploreVideos.ExploreVideosResponse;
import com.costar.talkwithidol.app.network.models.promo.PromoResponse;
import com.costar.talkwithidol.app.network.models.talkidols.TalkIdolsResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channellist.ChannelListFragment;
import com.costar.talkwithidol.ui.fragments.commuitylist.CommunityListFragment;
import com.costar.talkwithidol.ui.fragments.eventlist.EventListFragment;
import com.costar.talkwithidol.ui.fragments.newslist.NewsListFragment;
import com.costar.talkwithidol.ui.fragments.videolist.VideoListFragment;

import io.reactivex.Observable;


public class HomeModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private final PreferencesManager preferencesManager;


    public HomeModel(AppCompatActivity activity, PadloktNetwork padloktNetwork,PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }

    public void startChannelListActivity() {
        ChannelListFragment.startChannellistActivity(activity);
    }


    public  Observable<LikeEntityReponse> getLikeEntittyObservable(LikeEntityParams likeEntityParams) {
        return padloktNetwork.likeEntity(likeEntityParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<AddToWatchlistResponse> getAddToWatchListObservable(AddToWatchlistParams addToWatchlistParams) {
        return padloktNetwork.addTowatchList(addToWatchlistParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }
    public Observable<EventState> getEventState(String id ) {
        return padloktNetwork.checkEventState(id, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public void startEventActivity(){
        EventListFragment.startEventActivity(activity);
    }

    public void startVideoActivity(){
        VideoListFragment.startVideoActivity(activity);
    }


    public void startNewsActivity(){
        NewsListFragment.startNewsActivity(activity);
    }

    public void startCommunityActivity(){
        CommunityListFragment.startCommunityActivity(activity);
    }

    public Observable<ExploreNewsResponse> getNewsObservable(String cookie) {
        return padloktNetwork.getNews(cookie);
    }

    public Observable<ExploreEventResponse> getEventsObservable(String cookie) {
        return padloktNetwork.getEvents(cookie);
    }

    public Observable<CarouselResponse> getCarouselObservable(String cookie) {
        return padloktNetwork.getCarousel(cookie);
    }

    public Observable<ExploreChannelResponse> getChannelObservable(String cookie) {
        return padloktNetwork.getChannel(cookie);
    }

    public Observable<ExploreVideosResponse> getVideoObservable(String cookie) {
        return padloktNetwork.getVideo(cookie);
    }

    public Observable<PromoResponse> getPromoObservable(String cookie) {
        return padloktNetwork.getPromo(cookie);
    }

    public Observable<ExploreCommunitylResponse> getCommentObservable(String cookie) {
        return padloktNetwork.getCommunity(cookie);
    }



    public Observable<TalkIdolsResponse> getTalkidolObservable(String cookie) {
        return padloktNetwork.getTalkidol(cookie);
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public Observable<TalkIdolsResponse> getTalkList(int page,String cookie) {
        String url =Constants.TALKWITHIDOL+"?page="+page;
        return padloktNetwork.getTalkidolPage(url,cookie);
    }

}


