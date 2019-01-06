package com.costar.talkwithidol.ui.activities.newsdetail.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.LikeEntity.LikeEntityReponse;
import com.costar.talkwithidol.app.network.models.newsDetail.NewsDetailResponse;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.DeniedDialog;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class NewsDtailView extends FrameLayout {

    public static Boolean isDialog = false;
    public AppCompatActivity activity;
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView textView;
    @BindView(R.id.news_image)
    ImageView news_image;
    @BindView(R.id.news_title)
    TextView news_title;
    @BindView(R.id.tv_news_description)
    TextView tv_news_description;
    @BindView(R.id.tv_like)
    TextView tv_like;
    @BindView(R.id.tv_talent_name)
    TextView tv_talent_name;
    @BindView(R.id.tv_total_loveN)
    TextView tv_total_love;
    @BindView(R.id.iv_like)
    ImageView iv_like;
    DeniedDialog deniedDialog;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    ImageView backButton;
    @BindView(R.id.like)
    RelativeLayout like;
    ShowLoadingDialog showLoadingDialog;

    WebSubsDialog webSubsDialog;
    ProgressDialog progressDialog;

    public NewsDtailView(@NonNull AppCompatActivity activity, DeniedDialog deniedDialog, ConfirmDialog confirmDialog,
                         CreditCardDialog creditCardDialog, WebSubsDialog webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.deniedDialog = deniedDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.webSubsDialog = webSubsDialog;
        showLoadingDialog = new ShowLoadingDialog(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.news_detail_layout, this);
        ButterKnife.bind(this);

        textView = toolbar.findViewById(R.id.toolbar_title);
        // textView.setData("News Detail");
        activity.setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        DrawableCompat.setTint(backButton.getDrawable(), ContextCompat.getColor(activity, R.color.main_color));
        backButton.setOnClickListener(v -> {
            activity.finish();
        });

    }

    public Observable<Object> likeClickObservable() {
        return RxView.clicks(like);
    }

    public void setNewsDetail(NewsDetailResponse newsDetailResponse) {

        news_title.setText(newsDetailResponse.getData().getNewsName());

        if (newsDetailResponse.getData().getDescription() == null) {

            tv_news_description.setText("No description");

        } else {

            tv_news_description.setText(Html.fromHtml(newsDetailResponse.getData().getDescription()));
        }


        if (newsDetailResponse.getData().getLikes().getUserLike()) {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_liked_filled));
            tv_like.setText("Liked");
        } else {
            iv_like.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_like_border));
            tv_like.setText("Like");
        }
        tv_talent_name.setText(newsDetailResponse.getData().getChannelName());

        String lastLiker = newsDetailResponse.getData().getLikes().getLastLiker();
        int totalLikes = newsDetailResponse.getData().getLikes().getTotalLikes();
        if (newsDetailResponse.getData().getLikes().getTotalLikes() == 0) {
            tv_total_love.setText("");
        } else if (newsDetailResponse.getData().getLikes().getTotalLikes() == 1) {
            tv_total_love.setText(lastLiker + " liked this");
        } else if (newsDetailResponse.getData().getLikes().getTotalLikes() > 1) {
            tv_total_love.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
        }

        Picasso.with(activity).load(newsDetailResponse.getData().getImageUrl()).into(news_image);


        if (newsDetailResponse.getData().getAccess().equals("denied")) {
            deniedDialog.setDialogData(newsDetailResponse.getData().getDenied().getMessage(),
                    newsDetailResponse.getData().getDenied().getFreetrial().getName(),
                    newsDetailResponse.getData().getDenied().getSubscribe().getName(),
                    newsDetailResponse.getData().getDenied().getFreetrial().getCode());
            deniedDialog.showDialog(true);
            isDialog = true;
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
            tv_total_love.setText("");
        } else if (totalLikes == 1) {
            tv_total_love.setText(lastLiker+" liked this");
        } else if (totalLikes > 1) {
            tv_total_love.setText(lastLiker + " " + "and" + " " + (totalLikes - 1) + " " + "others liked this");
        }


    }


}
