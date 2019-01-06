package com.costar.talkwithidol.ui.fragments.channelcommunity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;

import com.costar.talkwithidol.ui.fragments.channelcommunity.dagger.CommunityModule;
import com.costar.talkwithidol.ui.fragments.channelcommunity.dagger.DaggerCommunityComponent;
import com.costar.talkwithidol.ui.fragments.channelcommunity.mvp.CommunityPresenter;
import com.costar.talkwithidol.ui.fragments.channelcommunity.mvp.CommunityView;

import javax.inject.Inject;


public class CommunityFragment extends Fragment {

    @Inject
    CommunityView communityView;

    @Inject
    CommunityPresenter communityPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerCommunityComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .communityModule(new CommunityModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        communityPresenter.onCreateView();
        AppUtils.transparentStatusBar(getActivity().getWindow());
        setHasOptionsMenu(true);


        return communityView;
    }


}
