package com.costar.talkwithidol.ui.activities.eventDetailpexip.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.EventDetailPexipModel;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.EventDetailPexipPresenter;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.EventDetailPexipView;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.QuestionListAdapter;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class EventDetailPexipModule {

    private final AppCompatActivity activity;

    public EventDetailPexipModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @EventDetailPexipScope
    @Provides
    public EventDetailPexipView eventDetailPexipView(DeniedDialog deniedDialog, ConfirmDialog confirmDialog,   CreditCardDialog creditCardDialog, QuestionListAdapter questionListAdapter,WebSubsDialog webSubsDialog) {
        return new EventDetailPexipView(activity, deniedDialog, confirmDialog, creditCardDialog, questionListAdapter, webSubsDialog);
    }

    @EventDetailPexipScope
    @Provides
    public EventDetailPexipModel eventDetailPexipModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new EventDetailPexipModel(activity, padloktNetwork, preferencesManager);
    }

    @EventDetailPexipScope
    @Provides
    public EventDetailPexipPresenter eventDetailPexipPresenter(EventDetailPexipView eventDetailPexipView, EventDetailPexipModel eventDetailPexipModel, PreferencesManager preferencesManager) {
        return new EventDetailPexipPresenter(eventDetailPexipView, eventDetailPexipModel, preferencesManager);
    }


    @EventDetailPexipScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @EventDetailPexipScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }
    @EventDetailPexipScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }

    @EventDetailPexipScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferenceManager) {
        return new CreditCardDialog(activity, preferenceManager);
    }


    @EventDetailPexipScope
    @Provides
    public QuestionListAdapter questionListAdapter() {
        return new QuestionListAdapter();
    }



}
