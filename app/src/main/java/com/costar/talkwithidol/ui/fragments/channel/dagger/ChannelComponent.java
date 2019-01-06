package com.costar.talkwithidol.ui.fragments.channel.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channel.ChannelFragment;

import dagger.Component;

@ChannelScope
@Component(modules = ChannelModule.class, dependencies = AppComponent.class)
public interface ChannelComponent {

    void inject(ChannelFragment channelFragment);
}
