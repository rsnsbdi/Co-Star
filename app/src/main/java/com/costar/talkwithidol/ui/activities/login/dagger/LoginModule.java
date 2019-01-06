package com.costar.talkwithidol.ui.activities.login.dagger;

import android.app.Activity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.mvp.ForgotPasswordDialog;
import com.costar.talkwithidol.ui.activities.login.mvp.LoginModel;
import com.costar.talkwithidol.ui.activities.login.mvp.LoginPresenter;
import com.costar.talkwithidol.ui.activities.login.mvp.LoginView;
import com.costar.talkwithidol.ui.dialog.EulaAcceptDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 8/7/2017.
 */
@Module
public class LoginModule {

    private final Activity activity;

    public LoginModule(Activity activity) {
        this.activity = activity;
    }

    @LoginScope
    @Provides
    public LoginView loginView(ForgotPasswordDialog forgotPasswordDialog, EulaAcceptDialog eulaAcceptDialog) {
        return new LoginView(activity, forgotPasswordDialog, eulaAcceptDialog);
    }

    @LoginScope
    @Provides
    public LoginModel loginModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new LoginModel(activity, padloktNetwork, preferencesManager);
    }


    @LoginScope
    @Provides
    public LoginPresenter loginPresenter(LoginView loginView, LoginModel loginModel) {
        return new LoginPresenter(loginView, loginModel);
    }


    @LoginScope
    @Provides
    public ForgotPasswordDialog forgotPasswordDialog() {
        return new ForgotPasswordDialog(activity);
    }


    @LoginScope
    @Provides
    public EulaAcceptDialog eulaAcceptDialog(PreferencesManager preferencesManager) {
        return new EulaAcceptDialog(activity, preferencesManager);
    }

}
