package com.costar.talkwithidol.ui.activities.eulaafterregister.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eulaafterregister.mvp.EulaModel;
import com.costar.talkwithidol.ui.activities.eulaafterregister.mvp.EulaPresenter;
import com.costar.talkwithidol.ui.activities.eulaafterregister.mvp.EulaView;

import dagger.Module;
import dagger.Provides;

@Module
public class EulaModule {


    public final AppCompatActivity activity;

    public EulaModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @EulaScope
    @Provides
    public EulaView eulaView(PreferencesManager preferencesManager)
    {
        return new EulaView(activity, preferencesManager);
    }

    @EulaScope
    @Provides
    public EulaModel eulaModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager)
    {
        return new EulaModel(activity, padloktNetwork,preferencesManager);
    }

    @EulaScope
    @Provides
    public EulaPresenter eulaPresenter(EulaView eulaView, EulaModel eulaModel)
    {
        return new EulaPresenter(eulaView, eulaModel);
    }




}
