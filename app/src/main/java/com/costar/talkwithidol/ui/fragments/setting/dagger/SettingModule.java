package com.costar.talkwithidol.ui.fragments.setting.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.account.AccountFragment;
import com.costar.talkwithidol.ui.fragments.channel.ChannelFragment;
import com.costar.talkwithidol.ui.fragments.preference.PreferenceFragment;
import com.costar.talkwithidol.ui.fragments.profile.ProfileFragment;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingModel;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingPagePagerAdapter;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingPresenter;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingView;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingModule {

    private final AppCompatActivity activity;

    public SettingModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @SettingScope
    @Provides
    public SettingView settingView(SettingPagePagerAdapter settingPagePagerAdapter, PreferencesManager preferencesManager) {
        return new SettingView(activity,settingPagePagerAdapter,preferencesManager);
    }

    @SettingScope
    @Provides
    public SettingModel settingModel(PadloktNetwork padloktNetwork, PreferencesManager preferenceManager) {
        return new SettingModel(activity, padloktNetwork, preferenceManager);
    }

    @SettingScope
    @Provides
    public SettingPresenter settingPresenter(SettingView settingView, SettingModel settingModel, PreferencesManager preferencesManager) {
        return new SettingPresenter(settingView, settingModel, preferencesManager);
    }

    @SettingScope
    @Provides
    public SettingPagePagerAdapter settingPagePagerAdapter(ProfileFragment profileFragment, ChannelFragment channelFragment, PreferenceFragment preferenceFragment, AccountFragment accountFragment)
    {
        return new SettingPagePagerAdapter(activity.getSupportFragmentManager(),profileFragment,channelFragment,preferenceFragment, accountFragment);
    }

    @SettingScope
    @Provides
    public ProfileFragment profileFragment()
    {
        return new ProfileFragment();
    }


    @SettingScope
    @Provides
    public ChannelFragment channelFragment()
    {
        return new ChannelFragment();
    }

    @SettingScope
    @Provides
    public PreferenceFragment preferenceFragment()
    {
        return new PreferenceFragment();
    }

    @SettingScope
    @Provides
    public AccountFragment accountFragment()
    {
        return new AccountFragment();
    }


}
