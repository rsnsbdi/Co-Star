package com.costar.talkwithidol.ui.activities.eulaafterregister.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.eulaafterregister.EulaRegisterActivity;

import dagger.Component;

@EulaScope
@Component(modules = EulaModule.class, dependencies = AppComponent.class)
public interface EulaComponent {

    void inject(EulaRegisterActivity eulaRegisterActivity);

}
