package com.costar.talkwithidol.ui.fragments.channelvideos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;

import com.costar.talkwithidol.ui.fragments.channelvideos.dagger.DaggerVideoComponent;
import com.costar.talkwithidol.ui.fragments.channelvideos.dagger.VideoModule;
import com.costar.talkwithidol.ui.fragments.channelvideos.mvp.VideoPresenter;
import com.costar.talkwithidol.ui.fragments.channelvideos.mvp.VideoView;

import javax.inject.Inject;


public class VideoFragment extends Fragment {

    @Inject
    VideoView videoView;

    @Inject
    VideoPresenter videoPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerVideoComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .videoModule(new VideoModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);

        videoPresenter.onCreate();
        AppUtils.transparentStatusBar(getActivity().getWindow());
        setHasOptionsMenu(true);

        return videoView;
    }


}
