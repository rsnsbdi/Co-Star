package com.costar.talkwithidol.ui.fragments.setting.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.profile.SettingsProfileResponse;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ViewConstructor")
public class SettingView extends FrameLayout {

    @BindView(R.id.vp_setting)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.nts_bottom)
    NavigationTabStrip navigationTabStrip;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_fullname)
    public  TextView full_name;
    private AppCompatActivity activity;

    @BindView(R.id.iv_auther_image)
    public  ImageView takeImage1;


    @BindView(R.id.iv_camera)
   public ImageView iv_camera;

    TextView tv_done;
    ShowLoadingDialog showLoadingDialog;
    SettingPagePagerAdapter settingPagePagerAdapter;
    ProgressDialog progressDialog = new ProgressDialog(getContext());

    public PreferencesManager preferencesManager;
    public SettingView(@NonNull AppCompatActivity activity, SettingPagePagerAdapter settingPagePagerAdapter,PreferencesManager preferencesManager) {
        super(activity);
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        this.settingPagePagerAdapter=settingPagePagerAdapter;
        showLoadingDialog = new ShowLoadingDialog(activity);
        inflate(activity, R.layout.fragment_setting, this);
        ButterKnife.bind(this);
        init();
    }


    public  void init(){
        viewPager.setAdapter(settingPagePagerAdapter);
        navigationTabStrip.setViewPager(viewPager, 0);
        tabLayout.setupWithViewPager(viewPager);
        activity.setSupportActionBar(toolbar);
        AppUtils.transparentStatusBar(activity.getWindow());
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        tv_done = toolbar.findViewById(R.id.tv_done);
        progressDialog.setMessage("processing...");
        viewPager.setOffscreenPageLimit(1);
    }

    public Activity getActivity(){
        return  activity;
    }


    public void setProfile(SettingsProfileResponse settingsProfileResponse) {
        if (settingsProfileResponse.getData().getFullName() != null){
            full_name.setText(settingsProfileResponse.getData().getFullName());
        }
        else{
            full_name.setText("");
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

    public void  showProgressDialog(boolean isLoading){
        if (isLoading){
            progressDialog.show();
        }else{
            progressDialog.cancel();
        }

    }


    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            showLoadingDialog.showDialog(true);
        } else {
            showLoadingDialog.showDialog(false);
        }

    }

    public void redirectToLogin() {
        preferencesManager.clear();
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
        activity.finish();
    }




}
