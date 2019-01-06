package com.costar.talkwithidol.ui.fragments.discover.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.discover.DiscoverFragment;

import dagger.Component;

@DiscoverScope
@Component(modules = DiscoverModule.class, dependencies = AppComponent.class)
public interface DiscoverComponent {

    void inject(DiscoverFragment discoverFragment);
}
