package com.costar.talkwithidol.ui.activities.homepage.dagger;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageModel;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePagePresenter;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;
import com.costar.talkwithidol.ui.fragments.channellist.ChannelListFragment;
import com.costar.talkwithidol.ui.fragments.commuitylist.CommunityListFragment;
import com.costar.talkwithidol.ui.fragments.discover.DiscoverFragment;
import com.costar.talkwithidol.ui.fragments.eventlist.EventListFragment;
import com.costar.talkwithidol.ui.fragments.home.HomeFragment;
import com.costar.talkwithidol.ui.fragments.newslist.NewsListFragment;
import com.costar.talkwithidol.ui.fragments.notification.NotificationFragment;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;
import com.costar.talkwithidol.ui.fragments.videolist.VideoListFragment;
import com.costar.talkwithidol.ui.fragments.watchlist.WatchListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 8/7/2017.
 */
@Module
public class HomePageModule {

    private final AppCompatActivity activity;

    public HomePageModule(AppCompatActivity activity) {
        this.activity = activity;
    }


    @HomePageScope
    @Provides
    public HomePageView homePageView(HomeFragment homeFragment,
                                     DiscoverFragment discoverFragment,
                                     NotificationFragment notificationFragment,
                                     SettingFragment settingFragment,
                                     WatchListFragment watchListFragment,
                                     NewsListFragment newsListFragment,
                                     VideoListFragment videoListFragment,
                                     ChannelListFragment channelListFragment,
                                     EventListFragment eventListFragment,
                                     CommunityListFragment communityListFragment,
                                     ChannelDetailFragment channelDetailFragment,
                                     PreferencesManager preferencesManager) {

        return new HomePageView(activity,
                homeFragment,
                discoverFragment,
                notificationFragment,
                settingFragment,
                watchListFragment,
                newsListFragment,
                videoListFragment,
                channelListFragment,
                eventListFragment,
                communityListFragment,
                channelDetailFragment,
                preferencesManager);
    }


    @HomePageScope
    @Provides
    public HomePageModel homePageModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new HomePageModel(activity, padloktNetwork, preferencesManager);
    }


    @HomePageScope
    @Provides
    public HomePagePresenter homePagePresenter(HomePageView homePageView, HomePageModel homePageModel, PreferencesManager preferencesManager) {
        return new HomePagePresenter(homePageView, homePageModel, preferencesManager);
    }


    @HomePageScope
    @Provides
    public HomeFragment homeFragment() {
        return new HomeFragment();
    }


    @HomePageScope
    @Provides
    public DiscoverFragment discoverFragment() {
        return new DiscoverFragment();
    }

    @HomePageScope
    @Provides
    public NotificationFragment notificationFragment() {
        return new NotificationFragment();
    }

    @HomePageScope
    @Provides
    public SettingFragment settingFragment() {
        return new SettingFragment();
    }


    @HomePageScope
    @Provides
    public WatchListFragment watchListFragment() {
        return new WatchListFragment();
    }

    @HomePageScope
    @Provides
    public NewsListFragment newsListFragment() {
        return new NewsListFragment();
    }

    @HomePageScope
    @Provides
    public ChannelListFragment channelListFragment() {
        return new ChannelListFragment();
    }

    @HomePageScope
    @Provides
    public CommunityListFragment communityListFragment() {
        return new CommunityListFragment();
    }

    @HomePageScope
    @Provides
    public VideoListFragment videoListFragment() {
        return new VideoListFragment();
    }

    @HomePageScope
    @Provides
    public EventListFragment eventListFragment() {
        return new EventListFragment();
    }

    @HomePageScope
    @Provides
    public ChannelDetailFragment channelDetailFragment() {
        return new ChannelDetailFragment();
    }

}
