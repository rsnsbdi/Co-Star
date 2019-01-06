package com.costar.talkwithidol.ui.activities.register.dagger;


import android.support.v7.app.AppCompatActivity;


import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.register.mvp.CodeDialog;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterDialog;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterModel;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterPresenter;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterView;
import com.costar.talkwithidol.ui.dialog.TermsPrivacyContact;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModule {


    public final AppCompatActivity activity;

    public RegisterModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @RegisterScope
    @Provides
    public RegisterView registerView(RegisterDialog registerDialog, CodeDialog codeDialog,TermsPrivacyContact termsPrivacyContact)
    {
        return new RegisterView(activity, registerDialog,codeDialog,termsPrivacyContact);
    }

    @RegisterScope
    @Provides
    public RegisterModel registerModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager)
    {
        return new RegisterModel(activity, padloktNetwork,preferencesManager);
    }

    @RegisterScope
    @Provides
    public RegisterPresenter registerPresenter(RegisterView registerView, RegisterModel registerModel)
    {
        return new RegisterPresenter(registerView, registerModel);
    }


    @RegisterScope
    @Provides
    public RegisterDialog registerDialog() {
        return new RegisterDialog(activity);
    }

    @RegisterScope
    @Provides
    public CodeDialog codeDialog() {
        return new CodeDialog(activity);
    }

    @RegisterScope
    @Provides
    public TermsPrivacyContact termsPrivacyContact() {
        return new TermsPrivacyContact(activity);
    }


}
