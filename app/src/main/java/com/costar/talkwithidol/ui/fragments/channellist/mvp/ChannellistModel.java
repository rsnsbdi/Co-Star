package com.costar.talkwithidol.ui.fragments.channellist.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.exploreChannel.ExploreChannelResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class ChannellistModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
   private   PreferencesManager preferencesManager;


    public ChannellistModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }
    public Observable<ExploreChannelResponse> getChannelObservable(int page, String cookie) {

        String url = Constants.ALLCHANNELS+"?page="+page;
        return padloktNetwork.getAllChannel(url, cookie);
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }


    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }
}


