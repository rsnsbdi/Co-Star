package com.costar.talkwithidol.ui.activities.eulaafterregister.mvp;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserEula.EulaResponse;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by shreedhar on 12/17/17.
 */

public class EulaView extends FrameLayout {
    ProgressDialog progressDialog;


    @BindView(R.id.title)
    TextView tv_title;

    @BindView(R.id.description)
    TextView tv_description;

    @BindView(R.id.btn_agree)
    Button agree;

    @BindView(R.id.btn_decline)
    Button decline;

    @BindView(R.id.loading_view)
    RelativeLayout loading;

    AppCompatActivity activity;
    PreferencesManager preferencesManager;

    public EulaView(AppCompatActivity activity1, PreferencesManager preferencesManager1) {
        super(activity1);
        this.activity = activity1;
        this.preferencesManager = preferencesManager1;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        inflate(activity, R.layout.eula_new, this);
        ButterKnife.bind(this);
        decline.setOnClickListener(v -> {
            preferencesManager.clear();
            Intent i = new Intent(activity, LoginActivity.class);
            activity.startActivity(i);
            activity.finish();
        });

    }

    public void setData(EulaResponse termsContactPrivacyResponse) {
        tv_title.setText(termsContactPrivacyResponse.getData().getTite());
//        tv_description.setText(Html.fromHtml(termsContactPrivacyResponse.getData().getDescription()));
//        String message = termsContactPrivacyResponse.getData().getDescription();
        String shortstring = AppUtils.getSafeSubstring(termsContactPrivacyResponse.getData().getDescription(), 100);
        //        Spannable descriptionSpannable = new SpannableString(shortstring + "...");

        shortstring = shortstring+"...more";
        tv_description.setText(Html.fromHtml(shortstring));
        tv_description.setOnClickListener(v -> {
            tv_description.setText(Html.fromHtml(termsContactPrivacyResponse.getData().getDescription()));
            tv_description.setClickable(false);
        });


//        Spannable morespannable = new SpannableString("more");
//        Spannable descriptionSpannable = new SpannableString(shortstring + "...");
//        descriptionSpannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.white)), 0, shortstring.length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_description.setText(Html.fromHtml(String.valueOf(descriptionSpannable)));
//        tv_description.append(morespannable);
//        ClickableSpan clickableSpan2 = new ClickableSpan() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(activity,"ajgbajbglasg",Toast.LENGTH_LONG).show();
////                expandTextView(tv_description);
//                tv_description.setText(Html.fromHtml(termsContactPrivacyResponse.getData().getDescription()));
//            }
//        };
//        morespannable.setSpan(clickableSpan2, 0, 4, 0);
//        morespannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.yellow1)), 0, 4, 0);
    }


    private void expandTextView(TextView tv) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", 500);
        animation.setDuration(200).start();
    }

    private void collapseTextView(TextView tv, int numLines) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
        } else
            progressDialog.dismiss();
    }

    public void showLoading(boolean doShow) {
        if (doShow) {
            loading.setVisibility(View.VISIBLE);
        } else
            loading.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Object> acceptButtonObservable() {
        return RxView.clicks(agree);
    }
}
