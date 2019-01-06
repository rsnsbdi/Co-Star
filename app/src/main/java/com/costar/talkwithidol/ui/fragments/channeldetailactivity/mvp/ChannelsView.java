package com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.PaydockCustomerResponse;
import com.costar.talkwithidol.app.network.models.channeldetals.ChannelDetailResponse;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.dialog.ConfirmDialog;
import com.costar.talkwithidol.ui.dialog.CreditCardDialog;
import com.costar.talkwithidol.ui.dialog.WebSubsDialogChannel;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import retrofit2.Callback;


@SuppressLint("ViewConstructor")
public class ChannelsView extends FrameLayout {

    public AppCompatActivity activity;
    @BindView(R.id.vp_channel)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.nts_bottom)
    NavigationTabStrip navigationTabStrip;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_channel)
    ImageView iv_channel;
    @BindView(R.id.tv_channel_name)
    TextView tv_channel_name;
    @BindView(R.id.iv_suscribe)
    ImageView iv_suscribe;
    PreferencesManager preferencesManager;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    ConfirmDialog confirmDialog;
    CreditCardDialog creditCardDialog;
    ImageView backButton;
    //    ShowLoadingDialog showLoading;
    ProgressDialog progressDialog;
    ChannelsPagePagerAdapter channelsPagePagerAdapter1;
    private Callback<PaydockCustomerResponse> likeEntityResponseCallback;
    WebSubsDialogChannel webSubsDialog;

    public ChannelsView(@NonNull AppCompatActivity activity, ConfirmDialog confirmDialog, CreditCardDialog creditCardDialog,
                        ChannelsPagePagerAdapter channelsPagePagerAdapter, PreferencesManager preferencesManager,
                        WebSubsDialogChannel webSubsDialog) {
        super(activity);
        this.activity = activity;
        this.creditCardDialog = creditCardDialog;
        this.confirmDialog = confirmDialog;
        this.creditCardDialog = creditCardDialog;
        this.channelsPagePagerAdapter1 = channelsPagePagerAdapter;
        this.webSubsDialog = webSubsDialog;
//        showLoading = new ShowLoadingDialog(activity);
        this.preferencesManager = preferencesManager;
        inflate(activity, R.layout.channels_activity, this);
        ButterKnife.bind(this);
        viewPager.setAdapter(channelsPagePagerAdapter1);
        channelsPagePagerAdapter1.notifyDataSetChanged(); //this line will force all pages to be loaded fresh when changing between fragments
        navigationTabStrip.setViewPager(viewPager, 0);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing...");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        Typeface fontTypeFace = Typeface.createFromAsset(activity.getAssets(), "MoonLight.otf");

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(fontTypeFace);
                }
            }
        }
        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        backButton = toolbar.findViewById(R.id.iv_home);
        backButton.setOnClickListener(v -> {
            RxBus.getInstance().send(1);
        });


    }

    public void setChannelDetail(ChannelDetailResponse channelDetailResponse) {

        rl_top.setVisibility(GONE);
        tv_channel_name.setText(channelDetailResponse.getData().getChannelName());
        Picasso.with(activity).load(channelDetailResponse.getData().getImageUrl()).into(iv_channel);
        if (channelDetailResponse.getData().getSubscribed() != null) {
            iv_suscribe.setVisibility(VISIBLE);
            if (channelDetailResponse.getData().getSubscribed()) {
                iv_suscribe.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.subscribed));
            } else {
                iv_suscribe.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.subscribe_cta));
            }
        } else {
            iv_suscribe.setVisibility(INVISIBLE);
        }


    }


    public Observable<Object> subscribeButtonObservable() {
        return RxView.clicks(iv_suscribe);
    }

    //detail click observable
    public Observable<Object> confirmButtonObservable() {
        return confirmDialog.confirmButtonObservable();
    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
//    public void showLoading(boolean isLoading){
//        if (isLoading){
//            showLoading.showDialog(true);
//        }else{
//            showLoading.showDialog(false);
//        }
//
//    }


}
