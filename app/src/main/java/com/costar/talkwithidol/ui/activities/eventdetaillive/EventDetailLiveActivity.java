package com.costar.talkwithidol.ui.activities.eventdetaillive;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.brightcove.player.event.EventType;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.dagger.DaggerEventDetailLiveComponent;
import com.costar.talkwithidol.ui.activities.eventdetaillive.dagger.EventDetailLiveModule;
import com.costar.talkwithidol.ui.activities.eventdetaillive.mvp.EventDetailLivePresenter;
import com.costar.talkwithidol.ui.activities.eventdetaillive.mvp.EventDetailLiveView;

import javax.inject.Inject;


public class EventDetailLiveActivity extends BaseActivity {

    @Inject
    EventDetailLiveView eventDetailLiveView;

    @Inject
    EventDetailLivePresenter eventDetailLivePresenter;

    public static String videoId;

    @Override
    public void onBackPressed() {
        if (eventDetailLiveView.isDialog) {
            finish();
        } else {
            super.onBackPressed();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerEventDetailLiveComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .eventDetailLiveModule(new EventDetailLiveModule(this))
                .build()
                .inject(this);
        setContentView(eventDetailLiveView);
        videoId = getIntent().getStringExtra("VIDEOID");
        eventDetailLivePresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventDetailLivePresenter.onDestroyView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        eventDetailLivePresenter.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            // Checks the orientation of the screen
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                eventDetailLiveView.eventEmitter.emit(EventType.ENTER_FULL_SCREEN);
                eventDetailLiveView.ic_close.setVisibility(View.GONE);
                eventDetailLiveView.event_detail.setVisibility(View.GONE);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                eventDetailLiveView.eventEmitter.emit(EventType.EXIT_FULL_SCREEN);
                eventDetailLiveView.ic_close.setVisibility(View.VISIBLE);
                eventDetailLiveView.event_detail.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }
    }
}
