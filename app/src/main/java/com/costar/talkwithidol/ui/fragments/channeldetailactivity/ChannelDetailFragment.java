package com.costar.talkwithidol.ui.fragments.channeldetailactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.dagger.ChannelsModule;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.dagger.DaggerChannelsComponent;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsPresenter;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp.ChannelsView;

import javax.inject.Inject;


public class ChannelDetailFragment extends Fragment {

    public static String videoId;
    @Inject
    ChannelsView channelsView;
    @Inject
    ChannelsPresenter channelsPresenter;

    public static void startChannelsActivity(Context context) {
        context.startActivity(new Intent(context, ChannelDetailFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerChannelsComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .channelsModule(new ChannelsModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        Bundle bundle = new Bundle();
        videoId = HomePageActivity.VIDEOID;
        channelsPresenter.onCreateView();

        return channelsView;

    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= channelsPresenter.getPreference();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewGroup mContainer = getActivity().findViewById(R.id.container);
        mContainer.removeAllViews();
        channelsPresenter.onDestroyView();


    }
}
