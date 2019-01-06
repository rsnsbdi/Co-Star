package com.costar.talkwithidol.ui.activities.homepage.mvp;

import android.app.Activity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.NotificationCountResponse.NotificationCountResponse;
import com.costar.talkwithidol.app.network.models.NtificationCountParam;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;

/**
 * Created by dell on 8/8/2017.
 */

public class HomePageModel {

    private final Activity activity;
    private final PadloktNetwork padloktNetwork;
    private final PreferencesManager preferencesManager;

    public HomePageModel(Activity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;
    }


    public Observable<NotificationCountResponse> getNotificationContObservable(NtificationCountParam ntificationCountParam) {
        return padloktNetwork.notificationCount(ntificationCountParam, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }






    public String getData(String key) {
        return preferencesManager.get(key);
    }

    public void saveData(String key, String value) {
        preferencesManager.save(key, value);
    }
}
