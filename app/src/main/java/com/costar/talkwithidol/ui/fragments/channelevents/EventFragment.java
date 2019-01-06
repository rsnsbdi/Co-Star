package com.costar.talkwithidol.ui.fragments.channelevents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.fragments.channelevents.dagger.DaggerEventComponent;
import com.costar.talkwithidol.ui.fragments.channelevents.dagger.EventModule;
import com.costar.talkwithidol.ui.fragments.channelevents.mvp.EventPresenter;
import com.costar.talkwithidol.ui.fragments.channelevents.mvp.EventView;

import javax.inject.Inject;


public class EventFragment extends Fragment {

    @Inject
    EventView eventView;

    @Inject
    EventPresenter eventPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerEventComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .eventModule(new EventModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);

        eventPresenter.onCreate();
        AppUtils.transparentStatusBar(getActivity().getWindow());
        setHasOptionsMenu(true);

        return eventView;
    }

}
