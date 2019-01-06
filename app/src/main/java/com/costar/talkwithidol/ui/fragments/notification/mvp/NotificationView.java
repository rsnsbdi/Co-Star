package com.costar.talkwithidol.ui.fragments.notification.mvp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.notificationlist.Datum;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class NotificationView extends FrameLayout {

    public AppCompatActivity activity;
    RecyclerView recyclerView;
    NotificationAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    //    ShowLoadingDialog showLoading;
    RelativeLayout loading;
    LinearLayoutManager layoutManager;


    public NotificationView(@NonNull AppCompatActivity activity, NotificationAdapter mAdapter) {
        super(activity);
        this.activity = activity;
        this.mAdapter = mAdapter;
//        showLoading = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.fragment_notifications, this);
        ButterKnife.bind(this);

        textView = toolbar.findViewById(R.id.toolbar_title);
        loading = findViewById(R.id.loading_view);
        textView.setText("Notifications");
        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);

    }

    public LinearLayoutManager layoutManager() {
        return layoutManager;
    }

    public void setNotificationList(ArrayList<Datum> notificationList) {
        mAdapter.showNotificationlist(notificationList, activity);

    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Datum> getDetailClickObservable() {
        return mAdapter.getDetailClickedObservable();
    }

    public void startEventDetail(String id, String mode) {


        if (mode.equals("upcoming")) {
            Intent intent = new Intent(activity, EventDtailActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);


        } else if (mode.equals("watch_live")) {

            Intent intent = new Intent(activity, EventDetailLiveActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        } else if (mode.equals("talent_vmr")) {


            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);

        } else if (mode.equals("participent_vmr")) {

            Intent intent = new Intent(activity, EventPretestActivity.class);
            intent.putExtra("VIDEOID", id);
            activity.startActivity(intent);
        }
    }

    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

}
