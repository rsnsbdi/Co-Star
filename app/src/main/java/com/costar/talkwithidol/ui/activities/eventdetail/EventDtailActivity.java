package com.costar.talkwithidol.ui.activities.eventdetail;

import android.os.Bundle;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.eventdetail.dagger.DaggerEventDtailComponent;
import com.costar.talkwithidol.ui.activities.eventdetail.dagger.EventDtailModule;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailPresenter;
import com.costar.talkwithidol.ui.activities.eventdetail.mvp.EventDtailView;

import javax.inject.Inject;


public class EventDtailActivity extends BaseActivity {

    public static String videoId;
    public static String from;
    @Inject
    EventDtailView eventDtailView;
    @Inject
    EventDtailPresenter eventDtailPresenter;

    @Override
    public void onBackPressed() {

        if (eventDtailView.isDialog) {
            finish();
        } else if (eventDtailView.isDialogA) {
            finish();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerEventDtailComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .eventDtailModule(new EventDtailModule(this))
                .build()
                .inject(this);

        setContentView(eventDtailView);
        videoId = getIntent().getStringExtra("VIDEOID");
        eventDtailPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventDtailPresenter.onDestroyView();

    }


}
