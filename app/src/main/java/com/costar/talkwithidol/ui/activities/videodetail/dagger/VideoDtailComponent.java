package com.costar.talkwithidol.ui.activities.videodetail.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;

import dagger.Component;

@VideoDtailScope
@Component(modules = VideoDtailModule.class, dependencies = AppComponent.class)
public interface VideoDtailComponent {

    void inject(VideoDtailActivity videoDtailActivity);
}
