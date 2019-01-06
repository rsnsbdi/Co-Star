package com.costar.talkwithidol.ui.fragments.channelcommunity.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityParams;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.exploreCommunity.ExploreCommunitylResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class CommunityModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    PreferencesManager preferencesManager;


    public CommunityModel(AppCompatActivity activity, PadloktNetwork padloktNetwork,PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }

    public Observable<ExploreCommunitylResponse> getchannelCommunityObservable(int page, String id, String cookie) {

        String url =Constants.CHANNELCOMMUNITY+id + "/community" +"?page="+page;
        return padloktNetwork.getChannelCommunity(url, cookie);
    }
    public  Observable<LikeEntityReponse> getLikeEntittyObservable(LikeEntityParams likeEntityParams) {
        return padloktNetwork.likeEntity(likeEntityParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }
    public String getData(String key) {
        return preferencesManager.get(key);
    }

}


