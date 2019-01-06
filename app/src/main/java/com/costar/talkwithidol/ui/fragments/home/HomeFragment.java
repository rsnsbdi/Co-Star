package com.costar.talkwithidol.ui.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.home.dagger.DaggerHomeComponent;
import com.costar.talkwithidol.ui.fragments.home.dagger.HomeModule;
import com.costar.talkwithidol.ui.fragments.home.mvp.HomePresenter;
import com.costar.talkwithidol.ui.fragments.home.mvp.HomeView;

import javax.inject.Inject;

import static com.costar.talkwithidol.ui.activities.homepage.HomePageActivity.backPressed;


public class HomeFragment extends Fragment {

    @Inject
    HomeView homeView;

    @Inject
    HomePresenter homePresenter;

    public static boolean resume=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        DaggerHomeComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .homeModule(new HomeModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);


        homePresenter.onCreateView();


        return homeView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();

        HomePageView.currentFragment= "home";
        RxBus.getInstance().send(0);
        if(!resume) {
          //  homePresenter.onCreateView();
        }

    }
}
