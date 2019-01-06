package com.costar.talkwithidol.ui.activities.splash.dagger;




import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.splash.SplashActivity;

import dagger.Component;

@SplashScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {

    void inject(SplashActivity splashActivity);
}
