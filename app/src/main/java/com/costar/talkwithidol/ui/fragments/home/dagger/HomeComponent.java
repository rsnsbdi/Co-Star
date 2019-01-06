package com.costar.talkwithidol.ui.fragments.home.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.home.HomeFragment;

import dagger.Component;

@HomeScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);
}
