package com.costar.talkwithidol.ui.activities.newsdetail;

import android.os.Bundle;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.dagger.DaggerNewsDtailComponent;
import com.costar.talkwithidol.ui.activities.newsdetail.dagger.NewsDtailModule;
import com.costar.talkwithidol.ui.activities.newsdetail.mvp.NewsDtailPresenter;
import com.costar.talkwithidol.ui.activities.newsdetail.mvp.NewsDtailView;

import javax.inject.Inject;


public class NewsDtailActivity extends BaseActivity {

    @Inject
    NewsDtailView newsDtailView;

    @Inject
    NewsDtailPresenter newsDtailPresenter;

    public static String videoId;
    @Override
    public void onBackPressed() {
        if (newsDtailView.isDialog) {
            finish();
        } else {
            super.onBackPressed();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerNewsDtailComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .newsDtailModule(new NewsDtailModule(this))
                .build()
                .inject(this);
        setContentView(newsDtailView);
        videoId = getIntent().getStringExtra("VIDEOID");
        newsDtailPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsDtailPresenter.onDestroyView();

    }


}
