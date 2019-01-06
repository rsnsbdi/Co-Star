package com.costar.talkwithidol.ui.activities.newsdetail.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.newsdetail.mvp.NewsDtailModel;
import com.costar.talkwithidol.ui.activities.newsdetail.mvp.NewsDtailPresenter;
import com.costar.talkwithidol.ui.activities.newsdetail.mvp.NewsDtailView;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDtailModule {

    private final AppCompatActivity activity;


    public NewsDtailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @NewsDtailScope
    @Provides
    public NewsDtailView newsDtailView(DeniedDialog deniedDialog, ConfirmDialog confirmDialog,  CreditCardDialog creditCardDialog,WebSubsDialog webSubsDialog ) {
        return new NewsDtailView(activity, deniedDialog, confirmDialog,  creditCardDialog, webSubsDialog);
    }

    @NewsDtailScope
    @Provides
    public NewsDtailModel newsDtailModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new NewsDtailModel(activity, padloktNetwork, preferencesManager);
    }

    @NewsDtailScope
    @Provides
    public NewsDtailPresenter newsDtailPresenter(NewsDtailView newsDtailView, NewsDtailModel newsDtailModel, PreferencesManager preferencesManager) {
        return new NewsDtailPresenter(newsDtailView, newsDtailModel, preferencesManager);
    }

    @NewsDtailScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @NewsDtailScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }

    @NewsDtailScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }
    @NewsDtailScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferenceManager) {
        return new CreditCardDialog(activity, preferenceManager);
    }


}
