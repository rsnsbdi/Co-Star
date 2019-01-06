package com.costar.talkwithidol.ui.activities.login.dagger;

import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;

import dagger.Component;

/**
 * Created by dell on 8/7/2017.
 */
@LoginScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
