package com.costar.talkwithidol.ui.fragments.setting.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;

import dagger.Component;

@SettingScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {

    void inject(SettingFragment settingFragment);
}
