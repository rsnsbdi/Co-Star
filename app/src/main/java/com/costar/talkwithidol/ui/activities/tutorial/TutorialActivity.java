package com.costar.talkwithidol.ui.activities.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.activities.tutorial.dagger.DaggerTutorialComponent;
import com.costar.talkwithidol.ui.activities.tutorial.dagger.TutorialModule;
import com.costar.talkwithidol.ui.activities.tutorial.mvp.TutorialView;

import javax.inject.Inject;

/**
 * Created by shreedhar on 10/28/17.
 */

public class TutorialActivity extends AppCompatActivity {

    public static void start(Context context)
    {
        context.startActivity(new Intent(context,TutorialActivity.class));
    }


    @Inject
    TutorialView tutorialView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerTutorialComponent.builder()
                .appComponent(PadloktApplication.get(this).appComponent())
                .tutorialModule(new TutorialModule(this))
                .build()
                .inject(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(tutorialView);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
