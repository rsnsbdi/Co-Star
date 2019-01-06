package com.costar.talkwithidol.ui.fragments.channelevents.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channelevents.EventFragment;

import dagger.Component;

@EventScope
@Component(modules = EventModule.class, dependencies = AppComponent.class)
public interface EventComponent {

    void inject(EventFragment eventFragment);
}
