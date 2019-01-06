package com.costar.talkwithidol.ui.fragments.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.discover.dagger.DaggerDiscoverComponent;
import com.costar.talkwithidol.ui.fragments.discover.dagger.DiscoverModule;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverPresenter;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverView;

import javax.inject.Inject;


public class DiscoverFragment extends Fragment {

    @Inject
    DiscoverView discoverView;

    @Inject
    DiscoverPresenter discoverPresenter;


    public static void startEventDetailActivity(Context context){
        context.startActivity(new Intent(context,EventDtailActivity.class));
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerDiscoverComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .discoverModule(new DiscoverModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);

        discoverPresenter.onCreateView();

        return discoverView;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= "discover";

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_shopping){

            Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
        }
        return  false;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
