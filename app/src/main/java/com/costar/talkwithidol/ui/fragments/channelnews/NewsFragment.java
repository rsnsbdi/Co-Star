package com.costar.talkwithidol.ui.fragments.channelnews;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;

import com.costar.talkwithidol.ui.fragments.channelnews.dagger.DaggerNewsComponent;
import com.costar.talkwithidol.ui.fragments.channelnews.dagger.NewsModule;
import com.costar.talkwithidol.ui.fragments.channelnews.mvp.NewsPresenter;
import com.costar.talkwithidol.ui.fragments.channelnews.mvp.NewsView;

import javax.inject.Inject;


public class NewsFragment extends Fragment {

    @Inject
    NewsView newsView;

    @Inject
    NewsPresenter newsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerNewsComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .newsModule(new NewsModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);

        newsPresenter.onCreate();

        AppUtils.transparentStatusBar(getActivity().getWindow());
        setHasOptionsMenu(true);

        return newsView ;
    }



}
