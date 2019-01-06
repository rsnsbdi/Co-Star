package com.costar.talkwithidol.ui.fragments.channelhome.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistParams;
import com.costar.talkwithidol.app.network.models.addtowatchlist.AddToWatchlistResponse;
import com.costar.talkwithidol.app.network.models.channelhome.ChannelHomeResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class ChannelHomeModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private final PreferencesManager preferencesManager;


    public ChannelHomeModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager=preferencesManager;

    }
    public Observable<ChannelHomeResponse> getChannelHomeObservable(int page, String id, String cookie) {
        String url =Constants.CHANNELHOME +id + "/home" +"?page="+page;

        return padloktNetwork.getChannelHome(url, cookie);
    }

    public  Observable<LikeEntityReponse> getLikeEntittyObservable(LikeEntityParams likeEntityParams) {
        return padloktNetwork.likeEntity(likeEntityParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
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


}


