package com.costar.talkwithidol.ui.fragments.watchlist.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListAdapter;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListModel;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListPresenter;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListView;

import dagger.Module;
import dagger.Provides;

@Module
public class WatchListModule {

    private final AppCompatActivity activity;

    public WatchListModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @WatchListScope
    @Provides
    public WatchListView watchListView(WatchListAdapter watchListAdapter, PreferencesManager preferencesManager) {
        return new WatchListView(activity, watchListAdapter,preferencesManager);
    }

    @WatchListScope
    @Provides
    public WatchListModel watchListModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new WatchListModel(activity, padloktNetwork, preferencesManager);
    }

    @WatchListScope
    @Provides
    public WatchListPresenter watchListPresenter(WatchListView watchListView, WatchListModel watchListModel) {
        return new WatchListPresenter(watchListView, watchListModel);
    }


    @WatchListScope
    @Provides
    public WatchListAdapter   watchListAdapter() {
        return new WatchListAdapter();
    }

   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
