package com.costar.talkwithidol.ui.activities.eventdetail.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;

import dagger.Component;

@EventDtailScope
@Component(modules = EventDtailModule.class, dependencies = AppComponent.class)
public interface EventDtailComponent {

    void inject(EventDtailActivity eventDtailActivity);
}
