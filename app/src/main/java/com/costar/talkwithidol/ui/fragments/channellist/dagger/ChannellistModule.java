package com.costar.talkwithidol.ui.fragments.channellist.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellistModel;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellistPresenter;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellistView;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellisttAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannellistModule {

    private final AppCompatActivity activity;

    public ChannellistModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ChannellistScope
    @Provides
    public ChannellistView channellistView(ChannellisttAdapter channellisttAdapter) {
        return new ChannellistView(activity, channellisttAdapter);
    }

    @ChannellistScope
    @Provides
    public ChannellistModel channellistModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new ChannellistModel(activity, padloktNetwork, preferencesManager);
    }

    @ChannellistScope
    @Provides
    public ChannellistPresenter channellistPresenter(ChannellistView channellistView, ChannellistModel channellistModel) {
        return new ChannellistPresenter(channellistView, channellistModel);
    }
    @ChannellistScope
    @Provides
    public ChannellisttAdapter channellisttAdapter() {
        return new ChannellisttAdapter();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
