package com.costar.talkwithidol.ui.fragments.eventlist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.eventlist.EventListFragment;

import dagger.Component;

@EventScope
@Component(modules = EventModule.class, dependencies = AppComponent.class)
public interface EventComponent {

    void inject(EventListFragment eventListFragment);
}
