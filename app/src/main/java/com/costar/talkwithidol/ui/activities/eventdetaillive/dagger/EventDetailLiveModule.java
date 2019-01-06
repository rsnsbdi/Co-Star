package com.costar.talkwithidol.ui.activities.eventdetaillive.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventdetaillive.mvp.EventDetailLiveModel;
import com.costar.talkwithidol.ui.activities.eventdetaillive.mvp.EventDetailLivePresenter;
import com.costar.talkwithidol.ui.activities.eventdetaillive.mvp.EventDetailLiveView;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class EventDetailLiveModule {

    private final AppCompatActivity activity;

    public EventDetailLiveModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @EventDetailLiveScope
    @Provides
    public EventDetailLiveView eventDetailLiveView(DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        return new EventDetailLiveView(activity, deniedDialog, confirmDialog,  creditCardDialog, webSubsDialog);
    }

    @EventDetailLiveScope
    @Provides
    public EventDetailLiveModel eventDetailLiveModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new EventDetailLiveModel(activity, padloktNetwork, preferencesManager);
    }

    @EventDetailLiveScope
    @Provides
    public EventDetailLivePresenter eventDetailLivePresenter(EventDetailLiveView eventDetailLiveView, EventDetailLiveModel eventDetailLiveModel, PreferencesManager preferencesManager) {
        return new EventDetailLivePresenter(eventDetailLiveView, eventDetailLiveModel, preferencesManager);
    }


    @EventDetailLiveScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @EventDetailLiveScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }
    @EventDetailLiveScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }

    @EventDetailLiveScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferenceManager) {
        return new CreditCardDialog(activity, preferenceManager);
    }




}
