package com.costar.talkwithidol.ui.fragments.channellist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channellist.ChannelListFragment;

import dagger.Component;

@ChannellistScope
@Component(modules = ChannellistModule.class, dependencies = AppComponent.class)
public interface ChannellistComponent {

    void inject(ChannelListFragment channelListFragment);
}
