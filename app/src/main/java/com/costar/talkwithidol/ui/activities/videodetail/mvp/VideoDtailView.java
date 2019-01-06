package com.costar.talkwithidol.ui.activities.videodetail.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.videoDetail.VideoDetailResponse;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class VideoDtailView extends FrameLayout {

    public static Boolean isDialog = false;
    @BindView(R.id.brightcove_video_view)
    public BrightcoveExoPlayerVideoView brightcove_video_view;
    public AppCompatActivity activity;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.tv_video_description)
    public TextView tv_video_description;
    @BindView(R.id.tv_talent_name)
    public TextView tv_talent_name;
    public EventEmitter eventEmitter;
    RecyclerView recyclerView;
    TextView textView;
    ShowLoadingDialog showLoadingDialog;
    @BindView(R.id.iv_like)
    ImageView iv_like;
    @BindView(R.id.like)
    RelativeLayout like;
    @BindView(R.id.tv_like)
    TextView tv_like;
    @BindView(R.id.tv_video_name)
    TextView tv_video_name;
    @BindView(R.id.tv_total_love1)
    TextView tv_total_love1;
    @BindView(R.id.rl_video)
    RelativeLayout rl_video;
    ;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    ImageView backButton;
    ProgressDialog progressDialog;
    LinearLayout videoView;
    WebSubsDialog webSubsDialog;

    public VideoDtailView(@NonNull AppCompatActivity activity, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.webSubsDialog = webSubsDialog;
        showLoadingDialog = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.video_detail_layout, this);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        textView = toolbar.findViewById(R.id.toolbar_title);
        //textView.setData("Video Detail");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v -> {
            activity.finish();
        });

    }

    public Observable<Object> likeClickObservable() {
        return RxView.clicks(like);
    }

    public void setVideoDtail(VideoDetailResponse videoDetailResponse) {

        tv_video_name.setText(videoDetailResponse.getData().getVideoName());

        if (videoDetailResponse.getData().getDescription() == null) {

            tv_video_description.setText("No description");

        } else {

            tv_video_description.setText(Html.fromHtml(videoDetailResponse.getData().getDescription()));
        }

        tv_talent_name.setText(videoDetailResponse.getData().getTalentName());

        if (videoDetailResponse.getData().getLikes().getUserLike()) {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_liked_filled));
            tv_like.setText("Liked");
        } else {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_like_border));
            tv_like.setText("Like");
        }

        String lastLiker = videoDetailResponse.getData().getLikes().getLastLiker();
        int totalLikes = videoDetailResponse.getData().getLikes().getTotalLikes();
        if (videoDetailResponse.getData().getLikes().getTotalLikes() == 0) {
            tv_total_love1.setText("");
        } else if (videoDetailResponse.getData().getLikes().getTotalLikes() == 1) {
            tv_total_love1.setText(lastLiker + " liked this");
        } else if (videoDetailResponse.getData().getLikes().getTotalLikes() > 1) {
            tv_total_love1.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
        }


        if (videoDetailResponse.getData().getSettings() != null) {
            eventEmitter = brightcove_video_view.getEventEmitter();
            Catalog catalog = new Catalog(eventEmitter, videoDetailResponse.getData().getSettings().getBrightcove().getAccount(), videoDetailResponse.getData().getSettings().getBrightcove().getPolicyKey());
            catalog.findVideoByReferenceID(videoDetailResponse.getData().getSettings().getBrightcove().getRefId(), new VideoListener() {

                // Add the video found to the queue with add().
                // Start playback of the video with start().
                @Override
                public void onVideo(Video video) {
                    Log.v("VIDEO", "onVideo: video = " + video);
                    brightcove_video_view.add(video);
                    brightcove_video_view.start();
                }
            });

            eventEmitter.emit(EventType.EXIT_FULL_SCREEN);
        }

        if (videoDetailResponse.getData().getAccess().equals("denied")) {
            deniedDialog.setDialogData(videoDetailResponse.getData().getDenied().getMessage(),
                    videoDetailResponse.getData().getDenied().getFreetrial().getName(),
                    videoDetailResponse.getData().getDenied().getSubscribe().getName(),
                    videoDetailResponse.getData().getDenied().getFreetrial().getCode());
            deniedDialog.showDialog(true);
            isDialog = true;
        }

        videoView = findViewById(R.id.videoView);
        videoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brightcove_video_view.isPlaying()) {
                    brightcove_video_view.pause();
                } else {
                    brightcove_video_view.start();
                }
            }
        });

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

    public void showProgressDialog(boolean isLoading) {
        if (isLoading) {
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        } else {
            progressDialog.dismiss();
        }

    }

    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            showLoadingDialog.showDialog(true);
        } else {
            showLoadingDialog.showDialog(false);
        }

    }

    public void setLike(LikeEntityReponse likeEntityReponse) {

        if (likeEntityReponse.getData().getStatus().equals("liked")) {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_liked_filled));
            tv_like.setText("Liked");
        } else {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_like_border));
            tv_like.setText("Like");
        }
        String lastLiker = likeEntityReponse.getData().getLast_liker();
        int totalLikes = likeEntityReponse.getData().getTotal();
        if (totalLikes == 0) {
            tv_total_love1.setText("");
        } else if (totalLikes == 1) {
            tv_total_love1.setText(lastLiker + " liked this");
        } else if (totalLikes > 1) {
            tv_total_love1.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
        }


    }

}
