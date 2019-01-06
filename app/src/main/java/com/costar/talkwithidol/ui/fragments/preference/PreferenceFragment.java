package com.costar.talkwithidol.ui.fragments.preference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.fragments.preference.dagger.DaggerPreferenceComponent;
import com.costar.talkwithidol.ui.fragments.preference.dagger.PreferenceModule;
import com.costar.talkwithidol.ui.fragments.preference.mvp.PreferencePresenter;
import com.costar.talkwithidol.ui.fragments.preference.mvp.PreferenceView;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;

import javax.inject.Inject;


public class PreferenceFragment extends Fragment {

    @Inject
    PreferenceView preferenceView;

    @Inject
    PreferencePresenter preferencePresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerPreferenceComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .preferenceModule(new PreferenceModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);

        SettingFragment.type = "preference";
        preferencePresenter.onCreateView();
        return preferenceView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
