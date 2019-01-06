package com.costar.talkwithidol.ui.activities.communitydetail.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommentAdapter;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommunityDtailModel;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommunityDtailPresenter;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommunityDtailView;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ReportDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityDtailModule {

    private final AppCompatActivity activity;

    public CommunityDtailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @CommunityDtailScope
    @Provides
    public CommunityDtailView communityDtailView(PreferencesManager preferencesManager, CommentAdapter commentAdapter, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog,ReportDialog reportDialog, WebSubsDialog webSubsDialog) {
        return new CommunityDtailView(preferencesManager, commentAdapter, activity, deniedDialog, confirmDialog, creditCardDialog, reportDialog, webSubsDialog);
    }

    @CommunityDtailScope
    @Provides
    public CommunityDtailModel communityDtailModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new CommunityDtailModel(activity, padloktNetwork, preferencesManager);
    }

    @CommunityDtailScope
    @Provides
    public CommunityDtailPresenter communityDtailPresenter(CommunityDtailView communityDtailView, CommunityDtailModel communityDtailModel, PreferencesManager preferencesManager) {
        return new CommunityDtailPresenter(communityDtailView, communityDtailModel, preferencesManager);
    }


    @CommunityDtailScope
    @Provides
    public CreditCardDialog creditCardDialog(PreferencesManager preferencesManager) {
        return new CreditCardDialog(activity, preferencesManager);
    }

    @CommunityDtailScope
    @Provides
    public WebSubsDialog webSubsDialog() {
        return new WebSubsDialog(activity);
    }
    @CommunityDtailScope
    @Provides
    public ConfirmDialog confirmDialog() {
        return new ConfirmDialog(activity);
    }

    @CommunityDtailScope
    @Provides
    public DeniedDialog deniedDialog() {
        return new DeniedDialog(activity);
    }

    @CommunityDtailScope
    @Provides
    public ReportDialog reportDialog() {
        return new ReportDialog(activity);
    }


    @CommunityDtailScope
    @Provides
    public CommentAdapter commentAdapter() {
        return new CommentAdapter();
    }




}
