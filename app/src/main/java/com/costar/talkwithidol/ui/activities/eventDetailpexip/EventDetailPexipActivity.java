package com.costar.talkwithidol.ui.activities.eventDetailpexip;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BasePexipActivity;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.dagger.DaggerEventDetailPexipComponent;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.dagger.EventDetailPexipModule;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.EventDetailPexipPresenter;
import com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp.EventDetailPexipView;

import javax.inject.Inject;


public class EventDetailPexipActivity extends BasePexipActivity {

    public static String videoId;
    @Inject
    EventDetailPexipView eventDetailPexipView;
    @Inject
    EventDetailPexipPresenter eventDetailPexipPresenter;

    @Override
    public void onBackPressed() {
        eventDetailPexipPresenter.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerEventDetailPexipComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .eventDetailPexipModule(new EventDetailPexipModule(this))
                .build()
                .inject(this);
        setContentView(eventDetailPexipView);
        videoId = getIntent().getStringExtra("VIDEOID");
        eventDetailPexipPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventDetailPexipPresenter.onDestroyView();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            eventDetailPexipView.lowerView.setVisibility(View.GONE);
            eventDetailPexipView.close.setVisibility(View.GONE);

//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            params.width =RelativeLayout.LayoutParams.MATCH_PARENT;
//            params.height =RelativeLayout.LayoutParams.MATCH_PARENT;
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            eventDetailPexipView.rl_fullview.setLayoutParams(params);




        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            eventDetailPexipView.lowerView.setVisibility(View.VISIBLE);
            eventDetailPexipView.close.setVisibility(View.VISIBLE);

//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
//            params.width =RelativeLayout.LayoutParams.MATCH_PARENT;
//            params.height =RelativeLayout.LayoutParams.MATCH_PARENT;
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            eventDetailPexipView.rl_fullview.setLayoutParams(params);




        }       }catch (Exception e){

        }
    }

}
