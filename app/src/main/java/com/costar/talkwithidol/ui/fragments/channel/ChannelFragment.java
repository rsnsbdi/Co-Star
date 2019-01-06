package com.costar.talkwithidol.ui.fragments.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.fragments.channel.dagger.ChannelModule;
import com.costar.talkwithidol.ui.fragments.channel.dagger.DaggerChannelComponent;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ChannelPresenter;
import com.costar.talkwithidol.ui.fragments.channel.mvp.ChannelView;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;


import javax.inject.Inject;


public class ChannelFragment extends Fragment {

    @Inject
    ChannelView channelView;

    @Inject
    ChannelPresenter channelPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerChannelComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .channelModule(new ChannelModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        SettingFragment.type = "channel";
        channelPresenter.onCreateView();

        return channelView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        channelPresenter.onCreateView();
    }
}
