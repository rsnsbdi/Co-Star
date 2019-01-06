package com.costar.talkwithidol.ui.activities.videodetail.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.videodetail.mvp.VideoDtailModel;
import com.costar.talkwithidol.ui.activities.videodetail.mvp.VideoDtailPresenter;
import com.costar.talkwithidol.ui.activities.videodetail.mvp.VideoDtailView;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoDtailModule {

    private final AppCompatActivity activity;

    public VideoDtailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @VideoDtailScope
    @Provides
    public VideoDtailView videoDtailView(DeniedDialog deniedDialog, ConfirmDialog confirmDialog,  CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        return new VideoDtailView(activity, deniedDialog, confirmDialog,  creditCardDialog, webSubsDialog);
    }

    @VideoDtailScope
    @Provides
    public VideoDtailModel videoDtailModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new VideoDtailModel(activity, padloktNetwork, preferencesManager);
    }

    @VideoDtailScope
    @Provides
    public VideoDtailPresenter videoDtailPresenter(VideoDtailView videoDtailView, VideoDtailModel videoDtailModel, PreferencesManager preferencesManager) {
        return new VideoDtailPresenter(videoDtailView, videoDtailModel, preferencesManager);
    }

    @VideoDtailScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @VideoDtailScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }
    @VideoDtailScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }


    @VideoDtailScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferenceManager) {
        return new CreditCardDialog(activity, preferenceManager);
    }


}
