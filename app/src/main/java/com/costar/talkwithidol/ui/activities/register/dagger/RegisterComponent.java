package com.costar.talkwithidol.ui.activities.register.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.register.RegisterActivity;

import dagger.Component;

@RegisterScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity registerActivity);

}
