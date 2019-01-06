package com.costar.talkwithidol.ui.activities.newsdetail.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;

import dagger.Component;

@NewsDtailScope
@Component(modules = NewsDtailModule.class, dependencies = AppComponent.class)
public interface NewsDtailComponent {

    void inject(NewsDtailActivity newsDtailActivity);
}
