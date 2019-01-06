package com.costar.talkwithidol.ui.activities.splash.dagger;


import android.app.Activity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.splash.mvp.SplashModel;
import com.costar.talkwithidol.ui.activities.splash.mvp.SplashPresenter;
import com.costar.talkwithidol.ui.activities.splash.mvp.views.DialogView;
import com.costar.talkwithidol.ui.activities.splash.mvp.views.SplashView;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    private final Activity activity;

    public SplashModule(Activity activity) {
        this.activity = activity;
    }

    @SplashScope
    @Provides
    public SplashView splashView(DialogView dialogView)
    {
        return new SplashView(activity,dialogView);
    }

    @SplashScope
    @Provides
    public DialogView dialogView()
    {
        return new DialogView(activity);
    }

    @SplashScope
    @Provides
    public SplashModel splashModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager)
    {
        return new SplashModel(activity,padloktNetwork, preferencesManager);
    }

    @SplashScope
    @Provides
    public SplashPresenter splashPresenter(SplashView splashView, SplashModel splashModel, PreferencesManager preferencesManager)
    {
        return new SplashPresenter(splashView,splashModel, preferencesManager);
    }

}
