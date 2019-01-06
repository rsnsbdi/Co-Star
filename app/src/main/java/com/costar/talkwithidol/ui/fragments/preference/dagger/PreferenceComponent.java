package com.costar.talkwithidol.ui.fragments.preference.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.preference.PreferenceFragment;

import dagger.Component;

@PreferenceScope
@Component(modules = PreferenceModule.class, dependencies = AppComponent.class)
public interface PreferenceComponent {

    void inject(PreferenceFragment preferenceFragment);
}
