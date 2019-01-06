package com.costar.talkwithidol.ui.fragments.channel.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.IosSubscriberDialog;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ChannelModel;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ChannelPresenter;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ChannelView;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ExpiringChannelAdapter;
import com.costar.talkwithidol.ui.fragments.channel.mvp.SubscribedChannelAdapter;
import com.costar.talkwithidol.ui.fragments.channel.mvp.UnSubscribedChannelAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelModule {

    private final AppCompatActivity activity;

    public ChannelModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ChannelScope
    @Provides
    public ChannelView channelView(DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog,
                                   SubscribedChannelAdapter subscribedChannelAdapter, UnSubscribedChannelAdapter unSubscribedChannelAdapter,
                                   ExpiringChannelAdapter expiringChannelAdapter, IosSubscriberDialog iosSubscriberDialog) {
        return new ChannelView(activity, deniedDialog, confirmDialog, creditCardDialog, subscribedChannelAdapter,
                unSubscribedChannelAdapter, expiringChannelAdapter, iosSubscriberDialog);
    }

    @ChannelScope
    @Provides
    public ChannelModel channelModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new ChannelModel(activity, padloktNetwork, preferencesManager);
    }

    @ChannelScope
    @Provides
    public ChannelPresenter channelPresenter(ChannelView channelView, ChannelModel channelModel, PreferencesManager preferencesManager) {
        return new ChannelPresenter(channelView, channelModel, preferencesManager);
    }


    @ChannelScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferencesManager) {
        return new CreditCardDialog(activity, preferencesManager);
    }


    @ChannelScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @ChannelScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }

    @ChannelScope
    @Provides
    public IosSubscriberDialog iosSubscriberDialog() {
        return new IosSubscriberDialog(activity);
    }


    @ChannelScope
    @Provides
    public SubscribedChannelAdapter subscribedChannelAdapter() {
        return new SubscribedChannelAdapter();
    }

    @ChannelScope
    @Provides
    public UnSubscribedChannelAdapter unSubscribedChannelAdapter() {
        return new UnSubscribedChannelAdapter();
    }

    @ChannelScope
    @Provides
    public ExpiringChannelAdapter expiringChannelAdapter() {
        return new ExpiringChannelAdapter();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
