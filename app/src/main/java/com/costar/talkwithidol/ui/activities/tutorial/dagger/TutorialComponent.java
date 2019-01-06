package com.costar.talkwithidol.ui.activities.tutorial.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.tutorial.TutorialActivity;

import dagger.Component;

@TutorialScope
@Component(modules = TutorialModule.class, dependencies = AppComponent.class)
public interface TutorialComponent {

    void inject(TutorialActivity tutorialActivity);
}
