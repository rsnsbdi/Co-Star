package com.costar.talkwithidol.ui.fragments.newslist;

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
import com.costar.talkwithidol.ui.fragments.newslist.dagger.DaggerNewsComponent;
import com.costar.talkwithidol.ui.fragments.newslist.dagger.NewsModule;
import com.costar.talkwithidol.ui.fragments.newslist.mvp.NewsPresenter;
import com.costar.talkwithidol.ui.fragments.newslist.mvp.NewsView;

import javax.inject.Inject;


public class NewsListFragment extends Fragment {

    @Inject
    NewsView newsView;

    @Inject
    NewsPresenter newsPresenter;

    public static void startNewsActivity(Context context){
        context.startActivity(new Intent(context,NewsListFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerNewsComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .newsModule(new NewsModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        newsPresenter.onCreateView();
        return newsView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsPresenter.onDestroyView();

    }
    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= newsPresenter.getPreference();

    }

}
