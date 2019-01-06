package com.costar.talkwithidol.ui.fragments.videolist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.videolist.VideoListFragment;

import dagger.Component;

@VideoScope
@Component(modules = VideoModule.class, dependencies = AppComponent.class)
public interface VideoComponent {

    void inject(VideoListFragment videoListFragment);
}
