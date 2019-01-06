package com.costar.talkwithidol.ui.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.fragments.profile.dagger.DaggerProfileComponent;
import com.costar.talkwithidol.ui.fragments.profile.dagger.ProfileModule;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ProfilePresenter;
import com.costar.talkwithidol.ui.fragments.profile.mvp.ProfileView;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingView;

import javax.inject.Inject;


public class ProfileFragment extends Fragment {

    @Inject
    ProfileView profileView;

    @Inject
    ProfilePresenter profilePresenter;

    SettingView settingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerProfileComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .profileModule(new ProfileModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);


        profilePresenter.onCreateView();
        SettingFragment.type = "profile";
        return profileView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        profilePresenter.onCreateView();
    }
}
