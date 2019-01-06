package com.costar.talkwithidol.ui.fragments.channelhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.fragments.channelhome.dagger.ChannelHomeModule;
import com.costar.talkwithidol.ui.fragments.channelhome.dagger.DaggerChannelHomeComponent;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomePresenter;
import com.costar.talkwithidol.ui.fragments.channelhome.mvp.ChannelHomeView;

import javax.inject.Inject;


public class ChannelHomeFragment extends Fragment {

    @Inject
    ChannelHomeView channelHomeView;

    @Inject
    ChannelHomePresenter channelHomePresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerChannelHomeComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .channelHomeModule(new ChannelHomeModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        channelHomePresenter.onCreateView();

        return channelHomeView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
