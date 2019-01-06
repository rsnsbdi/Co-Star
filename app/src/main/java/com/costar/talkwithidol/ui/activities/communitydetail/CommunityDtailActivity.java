package com.costar.talkwithidol.ui.activities.communitydetail;

import android.os.Bundle;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.communitydetail.dagger.CommunityDtailModule;
import com.costar.talkwithidol.ui.activities.communitydetail.dagger.DaggerCommunityDtailComponent;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommunityDtailPresenter;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.CommunityDtailView;

import javax.inject.Inject;


public class CommunityDtailActivity extends BaseActivity {

    public static String videoId;
    @Inject
    CommunityDtailView communityDtailView;
    @Inject
    CommunityDtailPresenter communityDtailPresenter;

    @Override
    public void onBackPressed() {
        if (communityDtailView.isReplyLayoutVisibility()) {
            communityDtailView.hideReplylayout();

        } else if (communityDtailView.isDialog) {
            finish();
        } else {
            super.onBackPressed();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerCommunityDtailComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .communityDtailModule(new CommunityDtailModule(this))
                .build()
                .inject(this);
        videoId = getIntent().getStringExtra("VIDEOID");
        setContentView(communityDtailView);
        communityDtailPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        communityDtailPresenter.onDestroyView();

    }

}
