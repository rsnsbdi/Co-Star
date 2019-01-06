package com.costar.talkwithidol.ui.fragments.notification.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationAdapter;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationModel;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationPresenter;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationView;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    private final AppCompatActivity activity;

    public NotificationModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @NotificationScope
    @Provides
    public NotificationView notificationView(NotificationAdapter notificationAdapter) {
        return new NotificationView(activity, notificationAdapter);
    }

    @NotificationScope
    @Provides
    public NotificationModel notificationModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new NotificationModel(activity, padloktNetwork, preferencesManager);
    }

    @NotificationScope
    @Provides
    public NotificationPresenter notificationPresenter(NotificationView notificationView, NotificationModel notificationModel) {
        return new NotificationPresenter(notificationView, notificationModel);
    }

    @NotificationScope
    @Provides
    public NotificationAdapter notificationAdapter() {
        return new NotificationAdapter(activity);
    }

   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
