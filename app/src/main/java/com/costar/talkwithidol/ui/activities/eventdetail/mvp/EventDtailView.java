package com.costar.talkwithidol.ui.activities.eventdetail.mvp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.eventDetail.EventsDetailResponse;
import com.costar.talkwithidol.app.network.models.questionSubmit.SubmitQuestionResponse;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class EventDtailView extends FrameLayout {

    public static Boolean isDialog = false;
    public static Boolean isDialogA = false;
    public AppCompatActivity activity;
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    @BindView(R.id.event_date)
    TextView event_date;
    @BindView(R.id.event_month_year)
    TextView event_month_year;
    @BindView(R.id.event_time)
    TextView event_time;
    @BindView(R.id.event_title)
    TextView event_title;
    @BindView(R.id.event_detail)
    TextView event_detail;
    @BindView(R.id.event_image)
    RoundedImageView event_image;
    ;
    @BindView(R.id.register_event)
    Button register_event;
    @BindView(R.id.image_detail)
    ImageView addTowatchlist;
    Dialog dialog = null;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    AfterSubmitDialog afterSubmitDialog;
    SubmitQuestionDialog submitQuestionDialog;
    ShowLoadingDialog showLoadingDialog;
    CompleteProfileDialog completeProfileDialog;
    ProgressDialog progressDialog;
    ImageView backButton;
    WebSubsDialog webSubsDialog;


    public EventDtailView(@NonNull AppCompatActivity activity, CompleteProfileDialog completeProfileDialog, AfterSubmitDialog afterSubmitDialog, SubmitQuestionDialog submitQuestionDialog, DeniedDialog deniedDialog, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.afterSubmitDialog = afterSubmitDialog;
        this.submitQuestionDialog = submitQuestionDialog;
        this.deniedDialog = deniedDialog;
        showLoadingDialog = new ShowLoadingDialog(activity);
        this.confirmDialog = confirmDialog;
        this.completeProfileDialog = completeProfileDialog;
        this.creditCardDialog = creditCardDialog;
        this.webSubsDialog = webSubsDialog;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.event_detail_layout, this);
        ButterKnife.bind(this);
        textView = toolbar.findViewById(R.id.toolbar_title);
        // textView.setData("Event Detail");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v -> {
            activity.finish();
        });

    }

    public void finish() {
        activity.finish();
    }

    public Observable<Object> addToWatchListObservable() {
        return RxView.clicks(addTowatchlist);
    }


    @SuppressLint("ResourceType")
    public void setEventsDetail(EventsDetailResponse eventsDetailResponse) {

        event_date.setText(String.valueOf(eventsDetailResponse.getData().getEventDate().getDay()));
        event_title.setText(eventsDetailResponse.getData().getEventName());
        //.setData(eventsDetailResponse.getData().getEventState());
        event_month_year.setText(eventsDetailResponse.getData().getEventDate().getMonth() + " " + eventsDetailResponse.getData().getEventDate().getYear());
        event_time.setText(String.valueOf(eventsDetailResponse.getData().getEventDate().getHour()) + ":" + String.valueOf(eventsDetailResponse.getData().getEventDate().getMinute()));
        event_detail.setText(Html.fromHtml(eventsDetailResponse.getData().getDescription()));
        Picasso.with(activity).load(eventsDetailResponse.getData().getImageUrl()).into(event_image);
        if (eventsDetailResponse.getData().getUser_watchlist()) {
            addTowatchlist.setImageResource(R.drawable.ic_event_added_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.holo_blue_dark));
        } else {
            addTowatchlist.setImageResource(R.drawable.ic_event_add_watchlist);
            DrawableCompat.setTint(addTowatchlist.getDrawable(), ContextCompat.getColor(activity, android.R.color.white));

        }

        try {
            if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("registered")) {
                register_event.setEnabled(false);
                register_event.setVisibility(View.VISIBLE);
                register_event.setText(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());
                register_event.setBackground(getResources().getDrawable(R.drawable.button_selector));


            } else if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("na")) {
                register_event.setVisibility(View.GONE);
                showMessage(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());
            } else {
                register_event.setEnabled(true);
                register_event.setVisibility(View.VISIBLE);
                register_event.setText(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());
                register_event.setBackground(getResources().getDrawable(R.drawable.red_button_selector));

            }
        } catch (Exception e) {

        }

        register_event.setOnClickListener(view -> {
            if (eventsDetailResponse.getData().getAccess().equals("granted")) {
                try {
                    if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("open")) {
                        showSubmitDialog(eventsDetailResponse);

                    } else if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("closed")) {
                        register_event.setVisibility(View.GONE);
                        showMessage("This event is closed");

                    } else if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("registered")) {
                        showMessage(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());

                    } else if (eventsDetailResponse.getData().getSettings().getQuestions().getState().equals("seeking")) {
                        showCompleteProfileDialog(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());
//                        completeProfileDialog.showDialog(true);


                    } else {
                        register_event.setVisibility(View.GONE);
                        showMessage(eventsDetailResponse.getData().getSettings().getQuestions().getMessage());
                    }
                } catch (Exception e) {
                    showMessage(e.getMessage());
                }

            } else {
                showMessage("Access Denied");
                if (eventsDetailResponse.getData().getAccess().equals("denied")) {
                    deniedDialog.setDialogData(eventsDetailResponse.getData().getDenied().getMessage(),
                            eventsDetailResponse.getData().getDenied().getFreetrial().getName(),
                            eventsDetailResponse.getData().getDenied().getSubscribe().getName(),
                            eventsDetailResponse.getData().getDenied().getFreetrial().getCode());
                    deniedDialog.showDialog(true);
                    isDialog = true;
                }
            }
        });
        if (eventsDetailResponse.getData().getAccess().equals("denied")) {
            deniedDialog.setDialogData(eventsDetailResponse.getData().getDenied().getMessage(),
                    eventsDetailResponse.getData().getDenied().getFreetrial().getName(),
                    eventsDetailResponse.getData().getDenied().getSubscribe().getName(),
                    eventsDetailResponse.getData().getDenied().getFreetrial().getCode());
            deniedDialog.showDialog(true);
            isDialog = true;
        }


    }


    public Observable<Object> getSendClick() {
        return submitQuestionDialog.sendButtonObservable();
    }


    public void showSubmitDialog(EventsDetailResponse eventsDetailResponse) {
        submitQuestionDialog.showDialogResponse(eventsDetailResponse);
    }

    public void showCompleteProfileDialog(String message) {
        completeProfileDialog.setData(message);
    }


    public void showAfterSubmitDialog(SubmitQuestionResponse submitQuestionResponse) {
        afterSubmitDialog.setDialog(submitQuestionResponse);

        submitQuestionDialog.showDialog(false);
        isDialogA = true;
    }


    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
    }

//    public void showDeniedDialog() {
//        deniedDialog.showDialog(true);
//    }
//
//
   /* public void openDiaog(Activity activity, EventsDetailResponse eventsDetailResponse) {
        dialog = new Dialog(activity, R.style.DialogStyle);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.event_live);

        dialog.show();
    }

    public void openDiaogNotstarted(Activity activity) {
        dialog = new Dialog(activity, R.style.DialogStyle);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.event_live_not_started);

        dialog.show();
    }*/


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


}
