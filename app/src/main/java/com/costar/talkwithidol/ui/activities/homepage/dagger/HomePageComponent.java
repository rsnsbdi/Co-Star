package com.costar.talkwithidol.ui.activities.homepage.dagger;

import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;

import dagger.Component;

/**
 * Created by dell on 8/7/2017.
 */

@HomePageScope
@Component(modules = HomePageModule.class,dependencies = AppComponent.class)
public interface HomePageComponent {

    void inject(HomePageActivity homePageActivity);

}
