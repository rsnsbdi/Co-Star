package com.costar.talkwithidol.ui.activities.tutorial.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.tutorial.mvp.TutorialPresenter;
import com.costar.talkwithidol.ui.activities.tutorial.mvp.TutorialView;

import dagger.Module;
import dagger.Provides;

@Module
public class TutorialModule {

    private final AppCompatActivity activity;

    public TutorialModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @TutorialScope
    @Provides
    public TutorialView tutorialView(PreferencesManager preferencesManager )
    {
        return new TutorialView(activity, preferencesManager);
    }


    @TutorialScope
    @Provides
    public TutorialPresenter tutorialPresenter(TutorialView tutorialView,   PreferencesManager preferencesManager)
    {
        return new TutorialPresenter(tutorialView, preferencesManager);
    }

}
