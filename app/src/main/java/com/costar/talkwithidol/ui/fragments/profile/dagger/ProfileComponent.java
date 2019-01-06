package com.costar.talkwithidol.ui.fragments.profile.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.profile.ProfileFragment;

import dagger.Component;

@ProfileScope
@Component(modules = ProfileModule.class, dependencies = AppComponent.class)
public interface ProfileComponent {

    void inject(ProfileFragment profileFragment);
}
