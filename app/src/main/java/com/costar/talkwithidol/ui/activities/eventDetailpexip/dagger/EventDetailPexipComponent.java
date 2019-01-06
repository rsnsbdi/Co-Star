package com.costar.talkwithidol.ui.activities.eventDetailpexip.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.EventDetailPexipActivity;

import dagger.Component;

@EventDetailPexipScope
@Component(modules = EventDetailPexipModule.class, dependencies = AppComponent.class)
public interface EventDetailPexipComponent {

    void inject(EventDetailPexipActivity eventDetailPexipActivity);
}
