package com.costar.talkwithidol.ui.fragments.discover.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;


public class DiscoverModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;


    public DiscoverModel(AppCompatActivity activity, PadloktNetwork padloktNetwork) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;

    }


  /*  public Observable<LoginResponse> pushStepsData(BackendPushParams backendPushParams) {
        return padloktNetwork.pushToBackend(backendPushParams);
    }

    public Observable<BackendRetrieveResponse> pullStepsData(BackendRetrieveParams backendRetrieveParams) {
        return padloktNetwork.retrieveFromBackend(backendRetrieveParams);
    }
*/

}


