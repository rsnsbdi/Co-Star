package com.costar.talkwithidol.ui.fragments.preference.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.fragments.preference.mvp.PreferenceModel;
import com.costar.talkwithidol.ui.fragments.preference.mvp.PreferencePresenter;
import com.costar.talkwithidol.ui.fragments.preference.mvp.PreferenceView;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {

    private final AppCompatActivity activity;

    public PreferenceModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @PreferenceScope
    @Provides
    public PreferenceView preferenceView(PreferencesManager preferencesManager) {
        return new PreferenceView(activity, preferencesManager);
    }

    @PreferenceScope
    @Provides
    public PreferenceModel preferenceModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new PreferenceModel(activity, padloktNetwork, preferencesManager);
    }

    @PreferenceScope
    @Provides
    public PreferencePresenter preferencePresenter(PreferenceView preferenceView, PreferenceModel preferenceModel, PreferencesManager preferencesManager) {
        return new PreferencePresenter(preferenceView, preferenceModel, preferencesManager);
    }

   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
