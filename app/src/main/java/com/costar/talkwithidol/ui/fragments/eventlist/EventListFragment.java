package com.costar.talkwithidol.ui.fragments.eventlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.eventlist.dagger.DaggerEventComponent;
import com.costar.talkwithidol.ui.fragments.eventlist.dagger.EventModule;
import com.costar.talkwithidol.ui.fragments.eventlist.mvp.EventPresenter;
import com.costar.talkwithidol.ui.fragments.eventlist.mvp.EventView;

import javax.inject.Inject;


public class EventListFragment extends Fragment {

    @Inject
    EventView eventView;

    @Inject
    EventPresenter eventPresenter;


    public static void startEventActivity(Context context){
        context.startActivity(new Intent(context,EventListFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerEventComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .eventModule(new EventModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        eventPresenter.onCreate();
        return eventView;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        eventPresenter.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= eventPresenter.getPreference();

    }
}
