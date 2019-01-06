package com.costar.talkwithidol.ui.fragments.discover.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverModel;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverPresenter;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverView;

import dagger.Module;
import dagger.Provides;

@Module
public class DiscoverModule {

    private final AppCompatActivity activity;

    public DiscoverModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @DiscoverScope
    @Provides
    public DiscoverView discoverView() {
        return new DiscoverView(activity);
    }

    @DiscoverScope
    @Provides
    public DiscoverModel discoverModel(PadloktNetwork padloktNetwork) {
        return new DiscoverModel(activity, padloktNetwork);
    }

    @DiscoverScope
    @Provides
    public DiscoverPresenter discoverPresenter(DiscoverView discoverView, DiscoverModel discoverModel) {
        return new DiscoverPresenter(discoverView, discoverModel);
    }




   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
