package com.costar.talkwithidol.ui.activities.homepage.mvp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 8/8/2017.
 */

public class HomePageView extends FrameLayout {


    public static String currentFragment = "";
    public PreferencesManager preferencesManager;

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.home_indicator)
    View homeIndicator;
    @BindView(R.id.discover_indicator)
    View discoverIndicator;
    @BindView(R.id.notification_indicator)
    View notificationIndicator;
    @BindView(R.id.setting_indicator)
    View settingIndicator;

    @BindView(R.id.notification_count)
    TextView notificationCount;
    @BindView(R.id.home)
    LinearLayout home;
    @BindView(R.id.discover)
    LinearLayout discover;
    @BindView(R.id.notification)
    LinearLayout notification;
    @BindView(R.id.setting)
    LinearLayout setting;
    @BindView(R.id.loading_view)
    RelativeLayout loading;

    HomeFragment homeFragment1;
    DiscoverFragment discoverFragment;
    NotificationFragment notificationFragment;
    SettingFragment settingFragment;
    WatchListFragment watchListFragment;
    NewsListFragment newsListFragment;
    VideoListFragment videoListFragment;
    ChannelListFragment channelListFragment;
    EventListFragment eventListFragment;
    CommunityListFragment communityListFragment;
    ChannelDetailFragment channelDetailFragment;
    FragmentManager fragmentManager;
    AppCompatActivity activity;
    FragmentTransaction fragmentTransaction;

    public HomePageView(@NonNull AppCompatActivity appCompatActivity,
                        HomeFragment homeFragment,
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
        super(appCompatActivity);
        this.activity = appCompatActivity;
        this.homeFragment1 = homeFragment;
        this.discoverFragment = discoverFragment;
        this.notificationFragment = notificationFragment;
        this.settingFragment = settingFragment;
        this.watchListFragment = watchListFragment;
        this.newsListFragment = newsListFragment;
        this.videoListFragment = videoListFragment;
        this.channelListFragment = channelListFragment;
        this.eventListFragment = eventListFragment;
        this.communityListFragment = communityListFragment;
        this.channelDetailFragment = channelDetailFragment;
        inflate(activity, R.layout.activity_homepage, this);
        this.preferencesManager = preferencesManager;
        ButterKnife.bind(this);

        loadFragment(homeFragment1, "home");
        onClick();

    }

    public void onClick() {
        home.setOnClickListener(v -> {
            homeFragment1 = new HomeFragment();
            loadFragment(homeFragment1, "home");
            showHomeIndicator();

        });
        discover.setOnClickListener(v -> {
            loadFragment(discoverFragment, "discover");
            showDiscoverIndicatior();


        });
        notification.setOnClickListener(v -> {
            loadFragment(notificationFragment, "notification");
            showNotificationIndicator();


        });
        setting.setOnClickListener(v -> {
            settingFragment = new SettingFragment();
            loadFragment(settingFragment, "settings");
            showSettingIndicator();


        });
    }


    public void switchFrg(String fragment) {
        switch (fragment) {
            case "channelList":
                channelListFragment = new ChannelListFragment();
                loadFragment(channelListFragment, fragment);
                break;
            case "newsList":
                newsListFragment = new NewsListFragment();
                loadFragment(newsListFragment, fragment);
                break;
            case "communityList":
                communityListFragment = new CommunityListFragment();
                loadFragment(communityListFragment, fragment);
                break;
            case "videoList":
                videoListFragment = new VideoListFragment();
                loadFragment(videoListFragment, fragment);
                break;
            case "eventList":
                eventListFragment = new EventListFragment();
                loadFragment(eventListFragment, fragment);
                break;
            case "channelDetail":
                channelDetailFragment = new ChannelDetailFragment();
                loadFragment(channelDetailFragment, fragment);
                break;
            case "watchList":
                watchListFragment = new WatchListFragment();
                loadFragment(watchListFragment, fragment);
                break;
            case "channelListH":
                channelListFragment = new ChannelListFragment();
                loadFragment(channelListFragment, "channelListH");
                break;
            case "newsListH":
                newsListFragment = new NewsListFragment();
                loadFragment(newsListFragment, "newsListH");
                break;
            case "communityListH":
                communityListFragment =  new CommunityListFragment();
                loadFragment(communityListFragment, "communityListH");
                break;
            case "videoListH":
                videoListFragment = new VideoListFragment();
                loadFragment(videoListFragment, "videoListH");
                break;
            case "eventListH":
                eventListFragment = new EventListFragment();
                loadFragment(eventListFragment, "eventListH");
                break;
            case "channelDetailH":
                channelDetailFragment = new ChannelDetailFragment();
                loadFragment(channelDetailFragment, "channelDetailH");
                break;
        }
    }


    public void showHomeIndicator() {
        homeIndicator.setVisibility(View.VISIBLE);
        discoverIndicator.setVisibility(View.GONE);
        notificationIndicator.setVisibility(View.GONE);
        settingIndicator.setVisibility(View.GONE);
    }

    public void showDiscoverIndicatior() {
        homeIndicator.setVisibility(View.GONE);
        discoverIndicator.setVisibility(View.VISIBLE);
        notificationIndicator.setVisibility(View.GONE);
        settingIndicator.setVisibility(View.GONE);
    }

    public void showNotificationIndicator() {
        homeIndicator.setVisibility(View.GONE);
        discoverIndicator.setVisibility(View.GONE);
        notificationIndicator.setVisibility(View.VISIBLE);
        settingIndicator.setVisibility(View.GONE);
    }

    public void showSettingIndicator() {
        homeIndicator.setVisibility(View.GONE);
        discoverIndicator.setVisibility(View.GONE);
        notificationIndicator.setVisibility(View.GONE);
        settingIndicator.setVisibility(View.VISIBLE);
    }

    public void loadFragment(Fragment fragment, String tag) {
        currentFragment = tag;
        preferencesManager.save("CurrentFragment",tag);
        changeIndicator();
        fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }




    public void loadFragmentHome(Fragment fragment, String tag) {
        currentFragment = tag;
        changeIndicator();
        fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.add(R.id.container, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void onBackPressed() {
        if (currentFragment.equalsIgnoreCase("home") ||
                currentFragment.equalsIgnoreCase("discover") ||
                currentFragment.equalsIgnoreCase("notification") ||
                currentFragment.equalsIgnoreCase("settings")) {
            activity.finish();
        } else if (currentFragment.equalsIgnoreCase("channelList") ||
                currentFragment.equalsIgnoreCase("newsList") ||
                currentFragment.equalsIgnoreCase("communityList") ||
                currentFragment.equalsIgnoreCase("videoList") ||
                currentFragment.equalsIgnoreCase("eventList") ||
                currentFragment.equalsIgnoreCase("channelDetail") ||
                currentFragment.equalsIgnoreCase("watchList")) {

            try {

                fragmentManager = activity.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(currentFragment)).commitAllowingStateLoss();
                fragmentManager.popBackStackImmediate();

            }catch (Exception e){

            }


        }else if(currentFragment.equalsIgnoreCase("channelListH") ||
                currentFragment.equalsIgnoreCase("newsListH") ||
                currentFragment.equalsIgnoreCase("communityListH") ||
                currentFragment.equalsIgnoreCase("videoListH") ||
                currentFragment.equalsIgnoreCase("eventListH") ||
                currentFragment.equalsIgnoreCase("channelDetailH") ||
                currentFragment.equalsIgnoreCase("watchListH")){

//            fragmentManager = activity.getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.remove(fragmentManager.findFragmentByTag(currentFragment)).commitAllowingStateLoss();
//            fragmentManager.popBackStackImmediate();



            homeFragment1 = new HomeFragment();
            loadFragment(homeFragment1, "home");
            showHomeIndicator();
        }
    }


    public void changeIndicator() {
        if (currentFragment.equalsIgnoreCase("channelList") ||
                currentFragment.equalsIgnoreCase("newsList") ||
                currentFragment.equalsIgnoreCase("communityList") ||
                currentFragment.equalsIgnoreCase("videoList") ||
                currentFragment.equalsIgnoreCase("eventList") ||
                currentFragment.equalsIgnoreCase("channelDetail") ||
                currentFragment.equalsIgnoreCase("watchList")||
                currentFragment.equalsIgnoreCase("channelListH") ||
                currentFragment.equalsIgnoreCase("newsListH") ||
                currentFragment.equalsIgnoreCase("communityListH") ||
                currentFragment.equalsIgnoreCase("videoListH") ||
                currentFragment.equalsIgnoreCase("eventListH") ||
                currentFragment.equalsIgnoreCase("channelDetailH") ||
                currentFragment.equalsIgnoreCase("watchListH")) {
            showDiscoverIndicatior();
        } else if (currentFragment.equalsIgnoreCase("notification")) {
            showNotificationIndicator();
        } else if (currentFragment.equalsIgnoreCase("settings")) {
            showSettingIndicator();
        } else if (currentFragment.equalsIgnoreCase("home")) {
            showHomeIndicator();
        }
    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
