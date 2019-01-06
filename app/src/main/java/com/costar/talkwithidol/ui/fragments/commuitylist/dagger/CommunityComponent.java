package com.costar.talkwithidol.ui.fragments.commuitylist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.commuitylist.CommunityListFragment;

import dagger.Component;

@CommunityScope
@Component(modules = CommunityModule.class, dependencies = AppComponent.class)
public interface CommunityComponent {

    void inject(CommunityListFragment communityListFragment);
}
