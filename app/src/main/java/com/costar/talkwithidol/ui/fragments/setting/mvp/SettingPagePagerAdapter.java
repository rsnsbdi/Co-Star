package com.costar.talkwithidol.ui.fragments.setting.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.costar.talkwithidol.ui.fragments.account.AccountFragment;
import com.costar.talkwithidol.ui.fragments.channel.ChannelFragment;
import com.costar.talkwithidol.ui.fragments.preference.PreferenceFragment;
import com.costar.talkwithidol.ui.fragments.profile.ProfileFragment;


public class SettingPagePagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles = {"Pofile","Channels","Preference","Account"};
    private ProfileFragment profileFragment;
    private ChannelFragment channelFragment;
    private PreferenceFragment preferenceFragment;
    private AccountFragment accountFragment;

    public SettingPagePagerAdapter(FragmentManager fm, ProfileFragment profileFragment, ChannelFragment channelFragment, PreferenceFragment preferenceFragment, AccountFragment accountFragment) {
        super(fm);

        this.profileFragment = profileFragment;
        this.channelFragment =channelFragment;
        this.preferenceFragment= preferenceFragment;
        this.accountFragment = accountFragment;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return profileFragment;

        }else if(position == 1){

            return channelFragment;

        }else if(position==2){

            return preferenceFragment;

        }else if(position==3){

            return accountFragment;

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
