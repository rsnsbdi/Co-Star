package com.costar.talkwithidol.ui.fragments.channeldetailactivity.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;

import dagger.Component;

@ChannelsScope
@Component(modules = ChannelsModule.class, dependencies = AppComponent.class)
public interface ChannelsComponent {

    void inject(ChannelDetailFragment channelDetailFragment);
}
