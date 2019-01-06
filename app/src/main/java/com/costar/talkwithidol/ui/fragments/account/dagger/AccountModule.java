package com.costar.talkwithidol.ui.fragments.account.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.dialog.ChangePasswordDialog;
import com.costar.talkwithidol.ui.dialog.TermsPrivacyContact;
import com.costar.talkwithidol.ui.fragments.account.mvp.AccountModel;
import com.costar.talkwithidol.ui.fragments.account.mvp.AccountPresenter;
import com.costar.talkwithidol.ui.fragments.account.mvp.AccountView;

import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {

    private final AppCompatActivity activity;

    public AccountModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @AccountScope
    @Provides
    public AccountView accountView(PreferencesManager preferencesManager, ChangePasswordDialog changePasswordDialog, TermsPrivacyContact termsPrivacyContact) {
        return new AccountView(activity, preferencesManager, changePasswordDialog, termsPrivacyContact);
    }

    @AccountScope
    @Provides
    public AccountModel accountModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new AccountModel(activity, padloktNetwork, preferencesManager);
    }

    @AccountScope
    @Provides
    public AccountPresenter accountPresenter(AccountView accountView, AccountModel accountModel) {
        return new AccountPresenter(accountView, accountModel);
    }

    @AccountScope
    @Provides
    public ChangePasswordDialog changePasswordDialog() {
        return new ChangePasswordDialog(activity);
    }

    @AccountScope
    @Provides
    public TermsPrivacyContact termsPrivacyContact() {
        return new TermsPrivacyContact(activity);
    }

   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
