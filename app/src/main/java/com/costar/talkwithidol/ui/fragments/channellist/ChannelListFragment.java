package com.costar.talkwithidol.ui.fragments.channellist;

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
import com.costar.talkwithidol.ui.fragments.channellist.dagger.ChannellistModule;
import com.costar.talkwithidol.ui.fragments.channellist.dagger.DaggerChannellistComponent;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellistPresenter;
import com.costar.talkwithidol.ui.fragments.channellist.mvp.ChannellistView;

import javax.inject.Inject;


public class ChannelListFragment extends Fragment {

    @Inject
    ChannellistView channellistView;

    @Inject
    ChannellistPresenter channellistPresenter;


    public static void startChannellistActivity(Context context){
        context.startActivity(new Intent(context,ChannelListFragment.class));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerChannellistComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .channellistModule(new ChannellistModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        channellistPresenter.onCreateView();
        return channellistView;
    }


    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment=channellistPresenter.getPreference();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        channellistPresenter.onDestroyView();

    }
}
