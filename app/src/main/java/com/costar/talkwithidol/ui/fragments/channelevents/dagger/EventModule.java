package com.costar.talkwithidol.ui.fragments.channelevents.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.adapters.EventAdapter;
import com.costar.talkwithidol.ui.fragments.channelevents.mvp.EventModel;
import com.costar.talkwithidol.ui.fragments.channelevents.mvp.EventPresenter;
import com.costar.talkwithidol.ui.fragments.channelevents.mvp.EventView;

import dagger.Module;
import dagger.Provides;

@Module
public class EventModule {

    private final AppCompatActivity activity;

    public EventModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @EventScope
    @Provides
    public EventView eventView(PreferencesManager preferencesManager, EventAdapter eventAdapter) {
        return new EventView(activity, preferencesManager, eventAdapter);
    }

    @EventScope
    @Provides
    public EventModel eventModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new EventModel(activity, padloktNetwork,preferencesManager);
    }

    @EventScope
    @Provides
    public EventPresenter eventPresenter(EventView eventView, EventModel eventModel) {
        return new EventPresenter(eventView, eventModel);
    }

    @EventScope
    @Provides
    public EventAdapter eventAdapter() {
        return new EventAdapter();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
