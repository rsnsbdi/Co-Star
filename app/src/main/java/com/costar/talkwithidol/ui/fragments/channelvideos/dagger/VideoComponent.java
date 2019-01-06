package com.costar.talkwithidol.ui.fragments.channelvideos.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channelvideos.VideoFragment;

import dagger.Component;

@VideoScope
@Component(modules = VideoModule.class, dependencies = AppComponent.class)
public interface VideoComponent {

    void inject(VideoFragment videoFragment);
}
