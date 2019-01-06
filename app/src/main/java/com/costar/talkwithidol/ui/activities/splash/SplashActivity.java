package com.costar.talkwithidol.ui.activities.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.splash.dagger.DaggerSplashComponent;
import com.costar.talkwithidol.ui.activities.splash.dagger.SplashModule;
import com.costar.talkwithidol.ui.activities.splash.mvp.SplashPresenter;
import com.costar.talkwithidol.ui.activities.splash.mvp.views.SplashView;

import javax.inject.Inject;


public class SplashActivity extends AppCompatActivity {


    @Inject
    SplashView splashView;

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSplashComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
        setContentView(splashView);
       /* String locale = SplashActivity.this.getResources().getConfiguration().locale.getCountry();
        System.out.println("Country=="+locale);

        TelephonyManager tm = (TelephonyManager)this.getSystemService(SplashActivity.this.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        System.out.println("Country1=="+countryCodeValue);*/
        AppUtils.transparentStatusBar(getWindow());
    }


    @Override
    protected void onResume() {
        super.onResume();
        splashPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.onDestroy();
    }
}
