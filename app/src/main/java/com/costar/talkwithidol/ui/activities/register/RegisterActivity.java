package com.costar.talkwithidol.ui.activities.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ui.activities.register.dagger.DaggerRegisterComponent;
import com.costar.talkwithidol.ui.activities.register.dagger.RegisterModule;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterPresenter;
import com.costar.talkwithidol.ui.activities.register.mvp.RegisterView;

import javax.inject.Inject;


public class RegisterActivity extends AppCompatActivity {

    public static void start(Context context)
    {
        context.startActivity(new Intent(context,RegisterActivity.class));
    }


    @Inject
    RegisterView registerView;

    @Inject
    RegisterPresenter registerPresenter;

    public static String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerRegisterComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);

        setContentView(registerView);
        registerPresenter.onCreate();
        AppUtils.transparentStatusBar(getWindow());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.onDestroy();

    }
}
