package com.costar.talkwithidol.ui.fragments.channelhome.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channelhome.ChannelHomeFragment;

import dagger.Component;

@ChannelHomeScope
@Component(modules = ChannelHomeModule.class, dependencies = AppComponent.class)
public interface ChannelHomeComponent {

    void inject(ChannelHomeFragment channelHomeFragment);
}
