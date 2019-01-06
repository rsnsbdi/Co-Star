package com.costar.talkwithidol.ui.fragments.home.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.home.mvp.CarouselPagerAdapter;
import com.costar.talkwithidol.ui.fragments.home.mvp.ChannelPagerAdapter;
import com.costar.talkwithidol.ui.fragments.home.mvp.CommunityPagerAdapter;
import com.costar.talkwithidol.ui.fragments.home.mvp.EventsPagerAdapter;
import com.costar.talkwithidol.ui.fragments.home.mvp.HomeModel;
import com.costar.talkwithidol.ui.fragments.home.mvp.HomePresenter;
import com.costar.talkwithidol.ui.fragments.home.mvp.HomeView;
import com.costar.talkwithidol.ui.fragments.home.mvp.NewsPagerAdapter;
import com.costar.talkwithidol.ui.fragments.home.mvp.VideosPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final AppCompatActivity activity;

    public HomeModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @HomeScope
    @Provides
    public HomeView homeView(CarouselPagerAdapter carouselPagerAdapter,ChannelPagerAdapter channelPagerAdapter, NewsPagerAdapter newsPagerAdapter, VideosPagerAdapter videosPagerAdapter, EventsPagerAdapter eventsPagerAdapter, CommunityPagerAdapter communityPagerAdapter) {
        return new HomeView(activity,carouselPagerAdapter,channelPagerAdapter,newsPagerAdapter, videosPagerAdapter, eventsPagerAdapter, communityPagerAdapter);
    }

    @HomeScope
    @Provides
    public HomeModel homeModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new HomeModel(activity, padloktNetwork, preferencesManager);
    }

    @HomeScope
    @Provides
    public HomePresenter homePresenter(HomeView homeView, HomeModel homeModel) {
        return new HomePresenter(homeView, homeModel);
    }

    @HomeScope
    @Provides
    public NewsPagerAdapter newsPagerAdapter() {
        return new NewsPagerAdapter();
    }
    @HomeScope
    @Provides
    public ChannelPagerAdapter channelPagerAdapter() {
        return new ChannelPagerAdapter();
    }

    @HomeScope
    @Provides
    public EventsPagerAdapter eventsPagerAdapter() {
        return new EventsPagerAdapter();
    }

    @HomeScope
    @Provides
    public CommunityPagerAdapter  communityPagerAdapter() {
        return new CommunityPagerAdapter();
    }
    @HomeScope
    @Provides
    public VideosPagerAdapter videosPagerAdapter() {
        return new VideosPagerAdapter();
    }
    @HomeScope
    @Provides
    public CarouselPagerAdapter carouselPagerAdapter() {
        return new CarouselPagerAdapter();
    }





}
