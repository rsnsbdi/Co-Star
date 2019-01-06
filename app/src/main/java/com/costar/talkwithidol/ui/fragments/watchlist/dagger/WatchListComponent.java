package com.costar.talkwithidol.ui.fragments.watchlist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.watchlist.WatchListFragment;

import dagger.Component;

@WatchListScope
@Component(modules = WatchListModule.class, dependencies = AppComponent.class)
public interface WatchListComponent {

    void inject(WatchListFragment watchListFragment);
}
