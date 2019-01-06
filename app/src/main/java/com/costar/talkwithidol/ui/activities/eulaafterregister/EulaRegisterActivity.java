package com.costar.talkwithidol.ui.activities.eulaafterregister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.eulaafterregister.dagger.DaggerEulaComponent;
import com.costar.talkwithidol.ui.activities.eulaafterregister.dagger.EulaModule;
import com.costar.talkwithidol.ui.activities.eulaafterregister.mvp.EulaPresenter;
import com.costar.talkwithidol.ui.activities.eulaafterregister.mvp.EulaView;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;

import javax.inject.Inject;

public class EulaRegisterActivity extends AppCompatActivity {

    @Inject
    EulaView eulaView;

    @Inject
    EulaPresenter eulaPresenter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, EulaRegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        LoginActivity.start(this);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerEulaComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .eulaModule(new EulaModule(this))
                .build()
                .inject(this);
        setContentView(eulaView);

        eulaPresenter.onCreate();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
