package com.costar.talkwithidol.ui.fragments.commuitylist;

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
import com.costar.talkwithidol.ui.fragments.commuitylist.dagger.CommunityModule;
import com.costar.talkwithidol.ui.fragments.commuitylist.dagger.DaggerCommunityComponent;
import com.costar.talkwithidol.ui.fragments.commuitylist.mvp.CommunityPresenter;
import com.costar.talkwithidol.ui.fragments.commuitylist.mvp.CommunityView;

import javax.inject.Inject;


public class CommunityListFragment extends Fragment {

    @Inject
    CommunityView communityView;

    @Inject
    CommunityPresenter communityPresenter;

    public static void startCommunityActivity(Context context){
        context.startActivity(new Intent(context,CommunityListFragment.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerCommunityComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .communityModule(new CommunityModule((AppCompatActivity)getActivity()))
                .build()
                .inject(this);
        communityPresenter.onCreateView();
        return communityView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        communityPresenter.onDestroyView();

    }
    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= communityPresenter.getPreference();

    }
}
