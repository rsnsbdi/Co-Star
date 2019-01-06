package com.costar.talkwithidol.ui.activities.childcomments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.BaseActivity;
import com.costar.talkwithidol.ui.activities.childcomments.dagger.ChildCommentModule;
import com.costar.talkwithidol.ui.activities.childcomments.dagger.DaggerChildCommentComponent;
import com.costar.talkwithidol.ui.activities.childcomments.mvp.ChildCommentPresenter;
import com.costar.talkwithidol.ui.activities.childcomments.mvp.ChildCommentsView;

import javax.inject.Inject;

/**
 * Created by shreedhar on 11/29/17.
 */

public class ChildCommentActivity extends BaseActivity {
    public static String videoId;
    public static String cid;

    @Inject
    ChildCommentPresenter childCommentPresenter;

    @Inject
    ChildCommentsView childCommentsView;



    public static void start(Context context){
        context.startActivity(new Intent(context,ChildCommentActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerChildCommentComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .childCommentModule(new ChildCommentModule(this))
                .build()
                .inject(this);

        setContentView(childCommentsView);
        videoId = getIntent().getStringExtra("VIDEOID");
        cid = getIntent().getStringExtra("CID");
        childCommentPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        childCommentPresenter.onDestroyView();

    }

}
