package com.costar.talkwithidol.ui.activities.videodetail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.brightcove.player.event.EventType;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.videodetail.dagger.DaggerVideoDtailComponent;
import com.costar.talkwithidol.ui.activities.videodetail.dagger.VideoDtailModule;
import com.costar.talkwithidol.ui.activities.videodetail.mvp.VideoDtailPresenter;
import com.costar.talkwithidol.ui.activities.videodetail.mvp.VideoDtailView;

import javax.inject.Inject;


public class VideoDtailActivity extends BaseActivity {

    @Inject
    VideoDtailView videoDtailView;

    @Inject
    VideoDtailPresenter videoDtailPresenter;


    private RelativeLayout.LayoutParams paramsNotFullscreen;

    public static String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerVideoDtailComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .videoDtailModule(new VideoDtailModule(this))
                .build()
                .inject(this);
        setContentView(videoDtailView);
        videoId = getIntent().getStringExtra("VIDEOID");
        videoDtailPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());


    }

    @Override
    public void onBackPressed() {
        if (videoDtailView.isDialog) {
            videoDtailPresenter.onPause();
            finish();
        } else {
            videoDtailPresenter.onPause();
            super.onBackPressed();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoDtailPresenter.onDestroyView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoDtailPresenter.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            // Checks the orientation of the screen
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                videoDtailView.eventEmitter.emit(EventType.ENTER_FULL_SCREEN);
                videoDtailView.toolbar.setVisibility(View.GONE);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                videoDtailView.eventEmitter.emit(EventType.EXIT_FULL_SCREEN);
                videoDtailView.toolbar.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }
    }

}
