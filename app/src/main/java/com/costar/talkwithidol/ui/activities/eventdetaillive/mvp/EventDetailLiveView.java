package com.costar.talkwithidol.ui.activities.eventdetaillive.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
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
public class EventDetailLiveView extends FrameLayout {

    public static Boolean isDialog = false;
    @BindView(R.id.ic_close)
    public ImageView ic_close;
    @BindView(R.id.event_detail)
    public TextView event_detail;
    public AppCompatActivity activity;
    public EventEmitter eventEmitter;
    RecyclerView recyclerView;
    @BindView(R.id.brightcove_video_view)
    BrightcoveExoPlayerVideoView brightcove_video_view;
    @BindView(R.id.event_title)
    TextView event_title;
    @BindView(R.id.tv_message)
    TextView tv_message;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    ShowLoadingDialog showLoadingDialog;
    ProgressDialog progressDialog;
    @BindView(R.id.image_detail)
    ImageView addTowatchlist;
    WebSubsDialog webSubsDialog;

    public EventDetailLiveView(@NonNull AppCompatActivity activity, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        showLoadingDialog = new ShowLoadingDialog(activity);
        this.creditCardDialog = creditCardDialog;
        this.webSubsDialog = webSubsDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.event_live_not_started, this);
        ButterKnife.bind(this);
        ic_close.setOnClickListener(v->{
            activity.finish();
        });


    }

    public Observable<Object> addToWatchListObservable() {
        return RxView.clicks(addTowatchlist);
    }


    public void setEventsDetail(EventsDetailResponse eventsDetailResponse) {

        event_title.setText(eventsDetailResponse.getData().getEventName());
        event_detail.setText(Html.fromHtml(eventsDetailResponse.getData().getDescription()));
        if (eventsDetailResponse.getData().getUser_watchlist()) {
            addTowatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            addTowatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));

        }
        if (eventsDetailResponse.getData().getAccess().equals("denied")) {
            deniedDialog.setDialogData(eventsDetailResponse.getData().getDenied().getMessage(),
                    eventsDetailResponse.getData().getDenied().getFreetrial().getName(),
                    eventsDetailResponse.getData().getDenied().getSubscribe().getName(),
                    eventsDetailResponse.getData().getDenied().getFreetrial().getCode());
            deniedDialog.showDialog(true);
            isDialog = true;
        }
        if (eventsDetailResponse.getData().getEventState().equalsIgnoreCase("live")) {

            tv_message.setVisibility(View.GONE);
            if (eventsDetailResponse.getData().getSettings() != null) {
                eventEmitter = brightcove_video_view.getEventEmitter();
                Catalog catalog = new Catalog(eventEmitter, eventsDetailResponse.getData().getSettings().getBrightcove().getAccount(), eventsDetailResponse.getData().getSettings().getBrightcove().getPolicyKey());
                catalog.findVideoByReferenceID(eventsDetailResponse.getData().getSettings().getBrightcove().getRefId(), new VideoListener() {

                    // Add the video found to the queue with add().
                    // Start playback of the video with start().
                    @Override
                    public void onVideo(Video video) {
                        Log.v("VIDEO", "onVideo: video = " + video);
                        brightcove_video_view.add(video);
                        brightcove_video_view.start();
                        tv_message.setVisibility(View.GONE);
                    }
                });

            }
        } else {
            brightcove_video_view.setVisibility(View.GONE);
            if (eventsDetailResponse.getData().getSettings() != null) {
                if (eventsDetailResponse.getData().getSettings().getPrelive_message() != null) {
                    tv_message.setText(eventsDetailResponse.getData().getSettings().getPrelive_message());
                }

            }
        }


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


    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
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


    public void setAddTowatchlist(boolean added) {
        if (added) {
            addTowatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            addTowatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));

        }

    }

}
