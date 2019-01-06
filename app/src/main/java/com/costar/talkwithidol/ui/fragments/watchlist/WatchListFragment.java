package com.costar.talkwithidol.ui.fragments.watchlist;

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
import com.costar.talkwithidol.ui.fragments.watchlist.dagger.DaggerWatchListComponent;
import com.costar.talkwithidol.ui.fragments.watchlist.dagger.WatchListModule;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListPresenter;
import com.costar.talkwithidol.ui.fragments.watchlist.mvp.WatchListView;

import javax.inject.Inject;


public class WatchListFragment extends Fragment {

    @Inject
    WatchListView watchListView;

    @Inject
    WatchListPresenter watchListPresenter;

    public static void startWatchListActivity(Context context){
        context.startActivity(new Intent(context,WatchListFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerWatchListComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .watchListModule(new WatchListModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        watchListPresenter.onCreateView();
        return watchListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        watchListPresenter.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= watchListPresenter.getPreference();

    }
}
