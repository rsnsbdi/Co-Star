package com.costar.talkwithidol.ui.fragments.channelcommunity.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.adapters.CommunityAdapter;
import com.costar.talkwithidol.ui.fragments.channelcommunity.mvp.CommunityModel;
import com.costar.talkwithidol.ui.fragments.channelcommunity.mvp.CommunityPresenter;
import com.costar.talkwithidol.ui.fragments.channelcommunity.mvp.CommunityView;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityModule {

    private final AppCompatActivity activity;

    public CommunityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @CommunityScope
    @Provides
    public CommunityView notificationView(CommunityAdapter communityAdapter) {
        return new CommunityView(activity, communityAdapter);
    }

    @CommunityScope
    @Provides
    public CommunityModel notificationModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new CommunityModel(activity, padloktNetwork,preferencesManager);
    }

    @CommunityScope
    @Provides
    public CommunityPresenter notificationPresenter(CommunityView communityView, CommunityModel communityModel) {
        return new CommunityPresenter(communityView, communityModel);
    }
    @CommunityScope
    @Provides
    public CommunityAdapter communityAdapter() {
        return new CommunityAdapter();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
