package com.costar.talkwithidol.ui.activities.homepage.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.costar.talkwithidol.ui.fragments.discover.DiscoverFragment;
import com.costar.talkwithidol.ui.fragments.home.HomeFragment;
import com.costar.talkwithidol.ui.fragments.notification.NotificationFragment;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;


public class HomePagePagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"Home","Discover","Notifications","Setting"};
    private HomeFragment homeFragment;
    private DiscoverFragment discoverFragment;
    private NotificationFragment notificationFragment;
    private SettingFragment settingFragment;


    public HomePagePagerAdapter(FragmentManager fm, HomeFragment homeFragment, DiscoverFragment discoverFragment, NotificationFragment notificationFragment, SettingFragment settingFragment) {
        super(fm);

        this.homeFragment = homeFragment;
        this.discoverFragment =discoverFragment;
        this.notificationFragment= notificationFragment;
        this.settingFragment = settingFragment;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return homeFragment;

        }else if(position == 1){

            return discoverFragment;

        }else if(position==2){

            return notificationFragment;

        }else if(position==3){

            return settingFragment;

        }

        return null ;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
