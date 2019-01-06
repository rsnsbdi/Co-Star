package com.costar.talkwithidol.ui.activities.eventdetaillive.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;

import dagger.Component;

@EventDetailLiveScope
@Component(modules = EventDetailLiveModule.class, dependencies = AppComponent.class)
public interface EventDetailLiveComponent {

    void inject(EventDetailLiveActivity eventDetailLiveActivity);
}
