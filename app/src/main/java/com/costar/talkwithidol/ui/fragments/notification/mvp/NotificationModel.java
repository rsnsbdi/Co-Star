package com.costar.talkwithidol.ui.fragments.notification.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.NotificationSeen.NotificationSeenResponse;
import com.costar.talkwithidol.app.network.models.eventstate.EventState;
import com.costar.talkwithidol.app.network.models.notificationlist.NotificationResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;


public class NotificationModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferencesManager;


    public NotificationModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        this.padloktNetwork = padloktNetwork;

    }


    public Observable<NotificationResponse> getNotificationlistObservable(int page,String cookie) {
        String url = Constants.NOTIFICATION+"?page="+page;
        return padloktNetwork.getNotification(url, cookie);
    }
    public Observable<NotificationSeenResponse> sendSeenstatus(String id) {
        String url = Constants.NOTIFICATION +id;
        return padloktNetwork.seenNotification(url, preferencesManager.get(Constants.COOKIE));
    }

    public Observable<EventState> getEventState(String id ) {
        return padloktNetwork.checkEventState(id,  preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public String getData(String key) {
        return preferencesManager.get(key);
    }


}


