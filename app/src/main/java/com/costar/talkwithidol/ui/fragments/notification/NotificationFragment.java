package com.costar.talkwithidol.ui.fragments.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.notification.dagger.DaggerNotificationComponent;
import com.costar.talkwithidol.ui.fragments.notification.dagger.NotificationModule;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationPresenter;
import com.costar.talkwithidol.ui.fragments.notification.mvp.NotificationView;

import javax.inject.Inject;


public class NotificationFragment extends Fragment {

    @Inject
    NotificationView notificationView;

    @Inject
    NotificationPresenter notificationPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerNotificationComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .notificationModule(new NotificationModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        notificationPresenter.onCreateView();

        return notificationView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        notificationPresenter.isClick = true;
        HomePageView.currentFragment= "notification";

         notificationPresenter.onCreateView();

    }
}
