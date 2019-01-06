package com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.costar.talkwithidol.ui.fragments.channelcommunity.CommunityFragment;
import com.costar.talkwithidol.ui.fragments.channelevents.EventFragment;
import com.costar.talkwithidol.ui.fragments.channelhome.ChannelHomeFragment;
import com.costar.talkwithidol.ui.fragments.channelnews.NewsFragment;
import com.costar.talkwithidol.ui.fragments.channelvideos.VideoFragment;


public class ChannelsPagePagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles = {"HOME", "EVENT", "VIDEOS", "NEWS", "COMMUNITY"};
    private ChannelHomeFragment homeFragment;
    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private CommunityFragment communityFragment;
    private EventFragment eventFragment;

    public ChannelsPagePagerAdapter(FragmentManager fm, ChannelHomeFragment homeFragment, NewsFragment newsFragment, VideoFragment videoFragment, CommunityFragment communityFragment, EventFragment eventFragment) {
        super(fm);

        this.homeFragment = homeFragment;
        this.newsFragment = newsFragment;
        this.videoFragment = videoFragment;
        this.communityFragment = communityFragment;
        this.eventFragment = eventFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return homeFragment;

        } else if (position == 1) {

            return eventFragment;

        } else if (position == 2) {

            return videoFragment;

        } else if (position == 3) {

            return newsFragment;

        } else if (position == 4) {

            return communityFragment;

        }

        return null;
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
