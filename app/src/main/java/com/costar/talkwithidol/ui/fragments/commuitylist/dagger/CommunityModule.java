package com.costar.talkwithidol.ui.fragments.commuitylist.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.adapters.CommunityAdapter;
import com.costar.talkwithidol.ui.fragments.commuitylist.mvp.CommunityModel;
import com.costar.talkwithidol.ui.fragments.commuitylist.mvp.CommunityPresenter;
import com.costar.talkwithidol.ui.fragments.commuitylist.mvp.CommunityView;

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
    public CommunityView communityView(CommunityAdapter communityAdapter) {
        return new CommunityView(activity, communityAdapter);
    }

    @CommunityScope
    @Provides
    public CommunityModel communityModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new CommunityModel(activity, padloktNetwork, preferencesManager);
    }

    @CommunityScope
    @Provides
    public CommunityPresenter communityPresenter(CommunityView communityView, CommunityModel communityModel) {
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
