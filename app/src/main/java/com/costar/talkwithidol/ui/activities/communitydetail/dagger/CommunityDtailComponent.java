package com.costar.talkwithidol.ui.activities.communitydetail.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;

import dagger.Component;

@CommunityDtailScope
@Component(modules = CommunityDtailModule.class, dependencies = AppComponent.class)
public interface CommunityDtailComponent {

    void inject(CommunityDtailActivity communityDtailActivity);
}
