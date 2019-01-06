package com.costar.talkwithidol.ui.activities.eventdetail.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.AfterSubmitDialog;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.CompleteProfileDialog;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailModel;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailPresenter;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailView;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.SubmitQuestionDialog;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class EventDtailModule {

    private final AppCompatActivity activity;

    public EventDtailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @EventDtailScope
    @Provides
    public EventDtailView eventDtailView(CompleteProfileDialog completeProfileDialog,AfterSubmitDialog afterSubmitDialog,SubmitQuestionDialog submitQuestionDialog, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        return new EventDtailView(activity, completeProfileDialog,afterSubmitDialog,submitQuestionDialog, deniedDialog, confirmDialog,  creditCardDialog, webSubsDialog);
    }

    @EventDtailScope
    @Provides
    public EventDtailModel eventDtailModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new EventDtailModel(activity, padloktNetwork, preferencesManager);
    }

    @EventDtailScope
    @Provides
    public EventDtailPresenter eventDtailPresenter(EventDtailView eventDtailView, EventDtailModel eventDtailModel, PreferencesManager preferencesManager) {
        return new EventDtailPresenter(eventDtailView, eventDtailModel, preferencesManager);
    }

    @EventDtailScope
    @Provides
    public SubmitQuestionDialog submitQuestionDialog() {
        return new SubmitQuestionDialog(activity);
    }

    @EventDtailScope
    @Provides
    public AfterSubmitDialog afterSubmitDialog() {
        return new AfterSubmitDialog(activity);
    }
    @EventDtailScope
    @Provides
    public CompleteProfileDialog completeProfileDialog() {
        return new CompleteProfileDialog(activity);
    }
    @EventDtailScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }
    @EventDtailScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @EventDtailScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }


    @EventDtailScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferenceManager) {
        return new CreditCardDialog(activity, preferenceManager);
    }

}
