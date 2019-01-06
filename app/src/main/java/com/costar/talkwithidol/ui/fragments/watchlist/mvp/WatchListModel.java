package com.costar.talkwithidol.ui.fragments.watchlist.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.exploreEvent.ExploreEventResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class WatchListModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferenceManager;

    public WatchListModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferenceManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferenceManager = preferenceManager;

    }


    public Observable<ExploreEventResponse> getWatchListObservable(int page, String cookie) {

        String url =Constants.WATCHLIST+"?page="+page;
        return padloktNetwork.getWatchlist(url, cookie);
    }

    public Observable<AddToWatchlistResponse> removeFromWatchlistObservable(AddToWatchlistParams addToWatchlistParams) {
        return padloktNetwork.removeFromWatchlist(addToWatchlistParams, preferenceManager.get(Constants.TOKEN), preferenceManager.get(Constants.COOKIE));
    }


    public String getData(String key) {
        return preferenceManager.get(key);
    }
    public void saveData(String key, String value) {
        preferenceManager.save(key, value);
    }

}


