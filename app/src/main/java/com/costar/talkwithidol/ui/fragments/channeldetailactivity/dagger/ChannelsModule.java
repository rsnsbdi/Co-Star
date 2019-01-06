package com.costar.talkwithidol.ui.fragments.channeldetailactivity.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialogChannel;
import com.costar.talkwithidol.ui.fragments.channelcommunity.CommunityFragment;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsModel;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsPagePagerAdapter;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsPresenter;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsView;
import com.costar.talkwithidol.ui.fragments.channelevents.EventFragment;
import com.costar.talkwithidol.ui.fragments.channelhome.ChannelHomeFragment;
import com.costar.talkwithidol.ui.fragments.channelnews.NewsFragment;
import com.costar.talkwithidol.ui.fragments.channelvideos.VideoFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelsModule {

    private final AppCompatActivity activity;

    public ChannelsModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ChannelsScope
    @Provides
    public ChannelsView channelsView(ChannelsPagePagerAdapter channelsPagePagerAdapter, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, PreferencesManager preferencesManager, WebSubsDialogChannel webSubsDialog) {
        return new ChannelsView(activity, confirmDialog, creditCardDialog, channelsPagePagerAdapter, preferencesManager, webSubsDialog);
    }

    @ChannelsScope
    @Provides
    public ChannelsModel channelsModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new ChannelsModel(activity, padloktNetwork, preferencesManager);
    }

    @ChannelsScope
    @Provides
    public ChannelsPresenter channelsPresenter(ChannelsView channelsView, ChannelsModel channelsModel, PreferencesManager preferencesManager) {
        return new ChannelsPresenter(channelsView, channelsModel, preferencesManager);
    }

    @ChannelsScope
    @Provides
    public ChannelsPagePagerAdapter channelsPagePagerAdapter(ChannelHomeFragment channelHomeFragment, NewsFragment newsFragment, VideoFragment videoFragment, CommunityFragment communityFragment, EventFragment eventFragment) {
        return new ChannelsPagePagerAdapter(activity.getSupportFragmentManager(), channelHomeFragment, newsFragment, videoFragment, communityFragment, eventFragment);
    }

    @ChannelsScope
    @Provides
    public ChannelHomeFragment homeFragment() {
        return new ChannelHomeFragment();
    }


    @ChannelsScope
    @Provides
    public NewsFragment newsFragment() {
        return new NewsFragment();
    }

    @ChannelsScope
    @Provides
    public VideoFragment videoFragment() {
        return new VideoFragment();
    }

    @ChannelsScope
    @Provides
    public CommunityFragment communityFragment() {
        return new CommunityFragment();
    }

    @ChannelsScope
    @Provides
    public EventFragment eventFragment() {
        return new EventFragment();
    }

    @ChannelsScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferencesManager) {
        return new CreditCardDialog(activity, preferencesManager);
    }


    @ChannelsScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @ChannelsScope
    @Provides
    public WebSubsDialogChannel webSubsDialog() {
        return new WebSubsDialogChannel(activity);
    }

}
