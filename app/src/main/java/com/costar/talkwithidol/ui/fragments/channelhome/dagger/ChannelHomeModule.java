package com.costar.talkwithidol.ui.fragments.channelhome.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomeAdapter;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomeModel;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomePresenter;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelHomeModule {

    private final AppCompatActivity activity;

    public ChannelHomeModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ChannelHomeScope
    @Provides
    public ChannelHomeView channelHomeView(PreferencesManager preferencesManager, ChannelHomeAdapter channelHomeAdapter) {
        return new ChannelHomeView(activity, preferencesManager, channelHomeAdapter);
    }

    @ChannelHomeScope
    @Provides
    public ChannelHomeModel channelHomeModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new ChannelHomeModel(activity, padloktNetwork, preferencesManager);
    }

    @ChannelHomeScope
    @Provides
    public ChannelHomePresenter channelHomePresenter(ChannelHomeView channelHomeView, ChannelHomeModel channelHomeModel) {
        return new ChannelHomePresenter(channelHomeView, channelHomeModel);
    }

    @ChannelHomeScope
    @Provides
    public ChannelHomeAdapter channelHomeAdapter() {
        return new ChannelHomeAdapter();
    }

   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
