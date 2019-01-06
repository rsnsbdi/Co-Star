package com.costar.talkwithidol.ui.activities.eventDetailpexip.mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.pexip.android.wrapper.PexView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class EventDetailPexipView extends FrameLayout {


    public static Boolean isDialog = false;
    public LinearLayout.LayoutParams paramsNotFullscreen;
    public AppCompatActivity activity;
    public PexView pexView;
    @BindView(R.id.selfview)
    WebView selfwebview;
    @BindView(R.id.event_title)
    TextView event_title;
    @BindView(R.id.event_detail)
    TextView event_detail;
    @BindView(R.id.iv_mic)
    ImageView iv_mic;
    @BindView(R.id.iv_hangup)
    ImageView iv_hangup;
    @BindView(R.id.iv_video)
    ImageView iv_video;
    @BindView(R.id.iv_full)
    ImageView iv_full;


    @BindView(R.id.rl_buttonView)
    public RelativeLayout rl_buttonView;

    /* @BindView(R.id.rl_tap)
     RelativeLayout rl_tap;*/
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_fullview)
    public RelativeLayout rl_fullview;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    boolean mic = true;
    boolean video = true;
    boolean full = true;
    ShowLoadingDialog showLoadingDialog;
    @BindView(R.id.image_detail)
    ImageView addTowatchlist;
    QuestionListAdapter questionListAdapter;
    ImageView backButton;

    @BindView(R.id.lower_view)
    public RelativeLayout lowerView;

    @BindView(R.id.rotate_view)
    public RelativeLayout rotateView;

    @BindView(R.id.ic_close)
    public ImageView close;
    int PERMISSION_ALL = 1;

    WebSubsDialog webSubsDialog;


    String[] PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
    ProgressDialog progressDialog;


    public EventDetailPexipView(@NonNull AppCompatActivity activity, DeniedDialog deniedDialog,
                                ConfirmDialog confirmDialog,   CreditCardDialog creditCardDialog,
                                QuestionListAdapter questionListAdapter, WebSubsDialog webSubsDialog) {
        super(activity);




        this.activity = activity;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.questionListAdapter = questionListAdapter;
        this.webSubsDialog = webSubsDialog;
        showLoadingDialog = new ShowLoadingDialog(activity);
        progressDialog = new ProgressDialog(activity);
        inflate(activity, R.layout.event_live, this);

        pexView = (PexView) findViewById(R.id.pexview);
        backButton = findViewById(R.id.ic_close);
        backButton.setOnClickListener(v -> {
            pexView.evaluateFunction("disconnect");
            pexView.stopLoading();
            activity.finish();
        });
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(questionListAdapter);

        iv_mic.setOnClickListener(view -> {

            if (mic == true) {
                pexView.evaluateFunction("muteAudio", true);
                mic = false;
                iv_mic.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_mic_off));
            } else {

                pexView.evaluateFunction("muteAudio", false);
                mic = true;
                iv_mic.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_mic));
            }

        });
        iv_video.setOnClickListener(view -> {


            if (video == true) {
                pexView.evaluateFunction("muteVideo", true);
                video = false;
                iv_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_video_off));
            } else {

                pexView.evaluateFunction("muteVideo", false);
                video = true;
                iv_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_video));
            }

        });
        iv_hangup.setOnClickListener(view -> {

            pexView.evaluateFunction("disconnect");
            pexView.stopLoading();
            activity.finish();

        });
        iv_full.setOnClickListener(v -> {
            if (full) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                lowerView.setVisibility(View.GONE);
                close.setVisibility(View.GONE);

//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                params.width =RelativeLayout.LayoutParams.MATCH_PARENT;
//                params.height =RelativeLayout.LayoutParams.MATCH_PARENT;
//                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                rl_fullview.setLayoutParams(params);

                //inflate(activity, R.layout.event_live, this);
                full = false;
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
               // inflate(activity, R.layout.event_live, this);
                lowerView.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
                full = true;
            }
        });


        //todo remove just for check
        //check();


    }

   /* public Observable<Object> rlTapObservable() {
        return RxView.clicks(rl_tap);
    }

    public void ShowRltap(Boolean b){
        if(b==true) {
            rl_tap.setVisibility(VISIBLE);
        }else{
            rl_tap.setVisibility(GONE);
        }
    }*/

    public Observable<Object> addToWatchListObservable() {
        return RxView.clicks(addTowatchlist);
    }

    public void setEventsDetail(EventsDetailResponse eventsDetailResponse) {

        if (eventsDetailResponse.getData().getSettings() != null) {

            event_title.setText(eventsDetailResponse.getData().getEventName());
            event_detail.setText(Html.fromHtml(eventsDetailResponse.getData().getDescription()));
            questionListAdapter.showList(eventsDetailResponse.getData().getSettings().getQuestions().getQuestionList());

            if(!hasPermissions(activity, PERMISSIONS)){
                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
            }else {
                //callbacks for events
                pexView.setEvent("onSetup", pexView.new PexEvent() {
                    @Override
                    public void onEvent(String[] strings) {

                        if (strings[1].toLowerCase().equals("required")) {
                            pexView.setSelfViewVideo(strings[0]);

                            pexView.evaluateFunction("connect", eventsDetailResponse.getData().getSettings().getPexip().getPin());


                        }else if (strings[1].toLowerCase().equals("none")){
                            pexView.setSelfViewVideo(strings[0]);
                            pexView.evaluateFunction("connect");
                        } else {
                            pexView.setVideo(strings[0]);
                            pexView.setVideo("connect");
                        }

                    }
                });


                pexView.setEvent("onConnect", pexView.new PexEvent() {
                    @Override
                    public void onEvent(String[] strings) {
                        if (strings.length > 0 && strings[0] != null)
                            pexView.setVideo(strings[0]);
                        //pexView.setSelfViewVideo(strings[0]);
                    }
                });


                pexView.setEvent("onCallTransfer", pexView.new PexEvent() {
                    @Override
                    public void onEvent(String[] strings) {

                        if (strings.length > 0 && strings[0] != null) {
                            //New vmr name that call is transferred to
                            String newConferenceName = strings[0];
                            //Do anything if required while transferring call...
                        }
                    }
                });


                pexView.addPageLoadedCallback(pexView.new PexCallback() {
                    @Override
                    public void callback(String returnValue) {
                        pexView.setSelfView(selfwebview);
                        String bandwidth  = "";
                        if(eventsDetailResponse.getData().getSettings().getPexip().getBandwidth()!=null){
                             bandwidth = eventsDetailResponse.getData().getSettings().getPexip().getBandwidth();
                        }else{
                             bandwidth ="512";
                        }
                        pexView.evaluateFunction("makeCall", eventsDetailResponse.getData().getSettings().getPexip().getDomain(), eventsDetailResponse.getData().getSettings().getPexip().getConference(), eventsDetailResponse.getData().getSettings().getPexip().getDisplayName(), bandwidth);

                    }
                });


                pexView.load();
            }
        } else {
            showMessage("Access Denied");
        }

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


    }

    //for test
    public void check(){
        if(!hasPermissions(activity, PERMISSIONS)){
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }else {
            //callbacks for events
            pexView.setEvent("onSetup", pexView.new PexEvent() {
                @Override
                public void onEvent(String[] strings) {

                    if (strings[1].toLowerCase().equals("required")) {
                        pexView.setSelfViewVideo(strings[0]);

//                            pexView.evaluateFunction("connect", eventsDetailResponse.getData().getSettings().getPexip().getPin());
                        pexView.evaluateFunction("connect","1111");

                    }else if (strings[1].toLowerCase().equals("none")){
                        pexView.setSelfViewVideo(strings[0]);
                        pexView.evaluateFunction("connect");
                    } else {
                        pexView.setSelfViewVideo(strings[0]);
                        pexView.evaluateFunction("connect");
                    }

                }
            });


            pexView.setEvent("onConnect", pexView.new PexEvent() {
                @Override
                public void onEvent(String[] strings) {
                    if (strings.length > 0 && strings[0] != null)
                        pexView.setVideo(strings[0]);
                    //pexView.setSelfViewVideo(strings[0]);
                }
            });


            pexView.setEvent("onCallTransfer", pexView.new PexEvent() {
                @Override
                public void onEvent(String[] strings) {

                    if (strings.length > 0 && strings[0] != null) {
                        //New vmr name that call is transferred to
                        String newConferenceName = strings[0];
                        //Do anything if required while transferring call...
                    }
                }
            });


            pexView.addPageLoadedCallback(pexView.new PexCallback() {
                @Override
                public void callback(String returnValue) {
                    pexView.setSelfView(selfwebview);
//                        pexView.evaluateFunction("makeCall", eventsDetailResponse.getData().getSettings().getPexip().getDomain(), eventsDetailResponse.getData().getSettings().getPexip().getConference(), eventsDetailResponse.getData().getSettings().getPexip().getDisplayName(), eventsDetailResponse.getData().getSettings().getPexip().getBandwidth());
                    pexView.evaluateFunction("makeCall", "mypadlokt.com", "test", "kishor1");
                }
            });

            pexView.setInitialScale(1);
            pexView.getSettings().setJavaScriptEnabled(true);
            pexView.getSettings().setLoadWithOverviewMode(true);
            pexView.getSettings().setUseWideViewPort(true);
            pexView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            pexView.setScrollbarFadingEnabled(false);
            pexView.load();

        }
    }

    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
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

    public void setAddTowatchlist(boolean added) {
        if (added) {
            addTowatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            addTowatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));

        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
