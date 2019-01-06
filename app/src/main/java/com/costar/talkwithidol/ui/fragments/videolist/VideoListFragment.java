package com.costar.talkwithidol.ui.fragments.videolist;

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
import com.costar.talkwithidol.ui.fragments.videolist.dagger.DaggerVideoComponent;
import com.costar.talkwithidol.ui.fragments.videolist.dagger.VideoModule;
import com.costar.talkwithidol.ui.fragments.videolist.mvp.VideoPresenter;
import com.costar.talkwithidol.ui.fragments.videolist.mvp.VideoView;

import javax.inject.Inject;


public class VideoListFragment extends Fragment {

    @Inject
    VideoView videoView;

    @Inject
    VideoPresenter videoPresenter;

    public static void startVideoActivity(Context context){
        context.startActivity(new Intent(context,VideoListFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerVideoComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .videoModule(new VideoModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        videoPresenter.onCreateView();
        return videoView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPresenter.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= videoPresenter.getPreference();

    }
}
