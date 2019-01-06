package com.costar.talkwithidol.ui.fragments.channelcommunity.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channelcommunity.CommunityFragment;

import dagger.Component;

@CommunityScope
@Component(modules = CommunityModule.class, dependencies = AppComponent.class)
public interface CommunityComponent {

    void inject(CommunityFragment notificationFragment);
}
