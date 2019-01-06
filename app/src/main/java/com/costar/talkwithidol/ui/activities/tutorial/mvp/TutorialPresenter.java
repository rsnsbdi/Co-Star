package com.costar.talkwithidol.ui.activities.tutorial.mvp;


import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.disposables.CompositeDisposable;

public class TutorialPresenter {

    private final TutorialView tutorialView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    PreferencesManager preferencesManager;




    public TutorialPresenter(TutorialView splashView,PreferencesManager preferencesManager) {
        this.tutorialView = splashView;
        this.preferencesManager = preferencesManager;

    }


    public void onDestroy() {
        compositeDisposable.clear();
    }





}
