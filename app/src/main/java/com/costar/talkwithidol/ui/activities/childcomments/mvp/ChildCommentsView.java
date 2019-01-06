package com.costar.talkwithidol.ui.activities.childcomments.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.Datum1;
import com.costar.talkwithidol.app.network.models.ChildReportid;
import com.costar.talkwithidol.app.network.models.PostComment.PostCommentParams;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.childcomments.ChildCommentActivity;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.ListChildCommentAdapter;
import com.costar.talkwithidol.ui.dialog.ReportDialog;
import com.costar.talkwithidol.ui.fragments.discover.mvp.DiscoverDTO;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by shreedhar on 11/29/17.
 */
@SuppressLint("ViewConstructor")
public class ChildCommentsView extends FrameLayout {
    public AppCompatActivity activity;
    RecyclerView recyclerView;
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    LinearLayoutManager layoutManager;

    ProgressDialog progressDialog;
    @BindView(R.id.tv_reply_post)
    EditText replyPost;
    @BindView(R.id.btn_reply_post)
    Button reply;

    @BindView(R.id.iv_reply_image)
    ImageView replyImage;

    ListChildCommentAdapter mAdapter;
    PreferencesManager preferencesManager;
    private List<DiscoverDTO> discoverList = new ArrayList<>();

    RelativeLayout loading;
    ReportDialog reportDialog;

    public ChildCommentsView(@NonNull AppCompatActivity activity, ListChildCommentAdapter channellisttAdapter, PreferencesManager preferencesManager, ReportDialog reportDialog) {
        super(activity);
        this.activity = activity;
        this.mAdapter = channellisttAdapter;
        this.preferencesManager = preferencesManager;
        this.reportDialog = reportDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.childcommentsnew, this);
        ButterKnife.bind(this);
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Comments");
        loading =findViewById(R.id.loading_view);
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v -> {
            activity.finish();
        });
        Picasso.with(activity).load(preferencesManager.get(Constants.USERIMAGE)).into(replyImage);

    }

    public void setChildComments(ArrayList<Datum1> exploreCommunityResponse) {
        mAdapter.showList(exploreCommunityResponse, activity);
    }

    public void showReportDialog(ChildReportid childReportid) {
        reportDialog.showDialog(true, childReportid.id, childReportid.userid);
    }


    public LinearLayoutManager layoutManager() {
        return layoutManager;
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


    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }

    }

    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        } else {
            progressDialog.dismiss();
        }

    }



    public Observable<Object> postReplyCLickObservable() {
        reply.setClickable(false);
        return RxView.clicks(reply);
    }


    public PostCommentParams postCommentParams() {
        return PostCommentParams.builder().communityId(ChildCommentActivity.cid).parentId(ChildCommentActivity.videoId).comment(replyPost.getText().toString().trim()).build();
    }

}
