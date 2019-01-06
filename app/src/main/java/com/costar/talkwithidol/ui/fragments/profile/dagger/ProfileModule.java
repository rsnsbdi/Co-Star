package com.costar.talkwithidol.ui.fragments.profile.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.register.mvp.CodeDialog;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ChangeEmailDialog;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ChangeMobileDialog;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ProfileModel;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ProfilePresenter;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ProfileView;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    private final AppCompatActivity activity;

    public ProfileModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ProfileScope
    @Provides
    public ProfileView profileView(ChangeMobileDialog changeMobileDialog, CodeDialog codeDialog, ChangeEmailDialog changeEmailDialog) {
        return new ProfileView(activity, changeMobileDialog, codeDialog, changeEmailDialog);
    }

    @ProfileScope
    @Provides
    public ProfileModel profileModel(PadloktNetwork padloktNetwork, PreferencesManager preferenceManager) {
        return new ProfileModel(activity, padloktNetwork,preferenceManager);
    }

    @ProfileScope
    @Provides
    public ProfilePresenter profilePresenter(ProfileView profileView, ProfileModel profileModel) {
        return new ProfilePresenter(profileView, profileModel, activity);
    }


    @ProfileScope
    @Provides
    public ChangeMobileDialog changeMobileDialog() {
        return new ChangeMobileDialog(activity);
    }


    @ProfileScope
    @Provides
    public CodeDialog codeDialog() {
        return new CodeDialog(activity);
    }

    @ProfileScope
    @Provides
    public ChangeEmailDialog changeEmailDialog() {
        return new ChangeEmailDialog(activity);
    }


}
