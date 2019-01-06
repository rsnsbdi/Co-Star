package com.costar.talkwithidol.ui.activities.communitydetail.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.PostComment.PostCommentParams;
import com.costar.talkwithidol.app.network.models.ReportId;
import com.costar.talkwithidol.app.network.models.commentsResponse.Datum;
import com.costar.talkwithidol.app.network.models.communityDetail.CommunityDetailResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.childcomments.ChildCommentActivity;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ReportDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class CommunityDtailView extends FrameLayout {

    public static String parentId = " ";
    public static String communityId = " ";
    public static Boolean isDialog = false;
    public AppCompatActivity activity;
    @BindView(R.id.iv_auther_image)
    RoundedImageView iv_auther_image;
    @BindView(R.id.tv_authername)
    TextView tv_authername;
    @BindView(R.id.tv_talent_name)
    TextView tv_talent_name;
    @BindView(R.id.tv_date_time)
    TextView dateTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;

    WebSubsDialog webSubsDialog;

    @BindView(R.id.rl_reply)
    RelativeLayout replyLayout;

    @BindView(R.id.comment)
    RelativeLayout commentLayout;


    @BindView(R.id.tv_reply_post)
    EditText replyPost;
    @BindView(R.id.btn_reply_post)
    Button reply;
    @BindView(R.id.btn_post)
    Button comment;
    @BindView(R.id.iv_reply_image)
    ImageView replyImage;
    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.et_comment_description)
    EditText commentDescription;
    CommentAdapter commentAdapter;
    PreferencesManager preferencesManager;
    ShowLoadingDialog showLoadingDialog;
    ProgressDialog progressDialog;
    LinearLayoutManager layoutManager;
    ImageView back;

    @BindView(R.id.detailview)
    LinearLayout detail;


    CommunityDetailResponse communityDetailResponse;

    ReportDialog reportDialog;
    ImageView communityImage;

    TextView tv_community_Description, desc_noimg;
    RelativeLayout likeview;
    TextView likeText, tv_total_love;
    ImageView likeicon;

    public CommunityDtailView(PreferencesManager preferencesManager, CommentAdapter commentAdapter,
                              @NonNull AppCompatActivity activity, DeniedDialog deniedDialog, ConfirmDialog confirmDialog,
                              CreditCardDialog creditCardDialog, ReportDialog reportDialog, WebSubsDialog webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        showLoadingDialog = new ShowLoadingDialog(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        this.commentAdapter = commentAdapter;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.webSubsDialog = webSubsDialog;
        this.reportDialog = reportDialog;
        inflate(activity, R.layout.newcommunitydetail, this);
        ButterKnife.bind(this);

        communityImage = findViewById(R.id.iv_image);
        likeicon = findViewById(R.id.iv_like);
        likeview = findViewById(R.id.like_view);
        likeText = findViewById(R.id.tv_like);
        tv_total_love = findViewById(R.id.tv_total_love);
        desc_noimg = findViewById(R.id.description_noimg);
        tv_community_Description = findViewById(R.id.tv_community_Description);

        layoutManager = new LinearLayoutManager(activity);
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        back = findViewById(R.id.iv_back);
        back.setOnClickListener(v -> {
            activity.finish();
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(commentAdapter.getType(), 0);
        layoutManager.scrollToPositionWithOffset(firstVisiblePosition, 0);
        Picasso.with(activity).load(preferencesManager.get(Constants.USERIMAGE)).into(replyImage);
        Picasso.with(activity).load(preferencesManager.get(Constants.USERIMAGE)).into(profileImage);


    }


    public void showReplyLayout(String parentCommentId) {
        replyLayout.setVisibility(View.VISIBLE);
        commentLayout.setVisibility(View.GONE);

        parentId = parentCommentId;
    }

    public void showReportDialog(ReportId reportId) {

        reportDialog.showDialog(true, reportId.id, reportId.userid);
    }

    public void hideReplylayout() {
        replyLayout.setVisibility(GONE);
        commentLayout.setVisibility(View.VISIBLE);
    }

    public Observable<Object> postReplyCLickObservable() {
        reply.setClickable(false);
        return RxView.clicks(reply);
    }

    public Observable<Object> postCommentClickObservable() {

        comment.setClickable(false);
        return RxView.clicks(comment);
    }


    public Observable<Object> likeclickobservable() {

        return RxView.clicks(likeview);
    }

    public Observable<String> getViewAllClickObservable() {
        return commentAdapter.getViewAllObservable();
    }

    public Observable<String> getReplyClickObservable() {
        return commentAdapter.getReplyObservable();
    }

    public void setCommunityDetail(CommunityDetailResponse communityDetailResponse) {
        this.communityDetailResponse = communityDetailResponse;

        //only set toolbar details
        communityId = communityDetailResponse.getData().getForumId();
        dateTime.setText(communityDetailResponse.getData().getForumDate());
        tv_authername.setText(communityDetailResponse.getData().getAuthorName());
        tv_talent_name.setText(communityDetailResponse.getData().getChannelName());
        Picasso.with(activity).load(communityDetailResponse.getData().getImageUrl()).into(iv_auther_image);
        if (communityDetailResponse.getData().getAccess().equals("denied")) {
            isDialog = true;
            deniedDialog.setDialogData(communityDetailResponse.getData().getDenied().getMessage(),
                    communityDetailResponse.getData().getDenied().getFreetrial().getName(),
                    communityDetailResponse.getData().getDenied().getSubscribe().getName(),
                    communityDetailResponse.getData().getDenied().getFreetrial().getCode());
            deniedDialog.showDialog(true);

        }


        tv_community_Description.setText(Html.fromHtml(communityDetailResponse.getData().getDescription()));
        if (communityDetailResponse.getData().getLikes().getUserLike()) {
            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.filled_love_icon));
            likeText.setText("Liked");
        } else {
            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfilled_love_icon));
            likeText.setText("Like");
        }

        String lastLiker = communityDetailResponse.getData().getLikes().getLastLiker();
        int totalLikes = communityDetailResponse.getData().getLikes().getTotalLikes();
        if (communityDetailResponse.getData().getLikes().getTotalLikes() == 0) {
            tv_total_love.setText("");
        } else if (communityDetailResponse.getData().getLikes().getTotalLikes() == 1) {
            tv_total_love.setText(lastLiker + " liked this");
        } else if (communityDetailResponse.getData().getLikes().getTotalLikes() > 1) {
            tv_total_love.setText(lastLiker + " " + "and" + " " + totalLikes + " " + "others liked this");
        }

        if (communityDetailResponse.getData().getHas_main_image()) {
            Picasso.with(activity).load(communityDetailResponse.getData().getImageUrl()).into(communityImage);
        } else {
            communityImage.setVisibility(View.GONE);
            desc_noimg.setText(Html.fromHtml(communityDetailResponse.getData().getDescription()));
            desc_noimg.setVisibility(View.VISIBLE);
            tv_community_Description.setVisibility(View.GONE);
        }


    }

    public void startChildComment(String parentId) {
        Intent i = new Intent(activity, ChildCommentActivity.class);
        i.putExtra("VIDEOID", parentId);
        i.putExtra("CID", CommunityDtailActivity.videoId);
        activity.startActivity(i);
    }

    public LinearLayoutManager layoutManager() {
        return layoutManager;
    }


    public void setCommunityComments(ArrayList<Datum> exploreEventResponse) {
        detail.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        commentAdapter.showList(activity, exploreEventResponse, communityDetailResponse);
    }

    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
    }

    public PostCommentParams postCommentParams() {
        return PostCommentParams.builder().communityId(communityId).parentId(parentId).comment(replyPost.getText().toString().trim()).build();
    }


    public PostCommentParams postCommentParams1() {
        return PostCommentParams.builder().communityId(communityId).parentId("").comment(commentDescription.getText().toString().trim()).build();
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
            showLoadingDialog.showDialog(true);
        } else {
            showLoadingDialog.showDialog(false);
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

    public Boolean isReplyLayoutVisibility() {
        if (replyLayout.getVisibility() == VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public void setLike(LikeEntityReponse likeEntityReponse) {
        commentAdapter.update(likeEntityReponse);
    }

    public void setLikeNC(LikeEntityReponse likeEntityReponse) {
        if (likeEntityReponse.getData().getStatus().equals("liked")) {
            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.filled_love_icon));
            likeText.setText("Liked");
        } else {
            likeicon.setImageDrawable(activity.getResources().getDrawable(R.drawable.unfilled_love_icon));
            likeText.setText("Like");
        }

        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        if (totalLikes == 0) {
            tv_total_love.setText("");
        } else if (totalLikes == 1) {
            tv_total_love.setText(lastLiker + " liked this");
        } else if (totalLikes > 1) {
            tv_total_love.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
        }
    }
}
