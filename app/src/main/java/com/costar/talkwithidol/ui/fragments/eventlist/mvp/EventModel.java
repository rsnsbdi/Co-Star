package com.costar.talkwithidol.ui.fragments.eventlist.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class EventModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private final PreferencesManager preferencesManager;


    public EventModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }
    public Observable<ExploreEventResponse> getEventsObservable(int page, String cookie) {

        String url =Constants.ALLEVENTS+"?page="+page;
        return padloktNetwork.getAllEvents(url, cookie);
    }


    public Observable<AddToWatchlistResponse> getAddToWatchListObservable(AddToWatchlistParams addToWatchlistParams) {
        return padloktNetwork.addTowatchList(addToWatchlistParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }


    public Observable<EventState> getEventState(String id ) {
        return padloktNetwork.checkEventState(id,  preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }
    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }
}


