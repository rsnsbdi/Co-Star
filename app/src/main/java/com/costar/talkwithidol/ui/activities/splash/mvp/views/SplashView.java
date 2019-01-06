package com.costar.talkwithidol.ui.activities.splash.mvp.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.AppUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ViewConstructor")
public class SplashView extends FrameLayout {


    final int pBarMax = 100;
    public Activity activity;
    public SharedPreferences sharedPreferencesN;
    @BindView(R.id.horizontal_progressBar)
    ProgressBar horizontal_progressBar;
    int progress = 0;
    @BindView(R.id.randomText)
    TextView randomText;

    public SplashView(@NonNull Activity activity, DialogView dialogView) {
        super(activity);
        this.activity = activity;
        inflate(activity, R.layout.activity_splash, this);
        ButterKnife.bind(this);
        sharedPreferencesN = PreferenceManager.getDefaultSharedPreferences(activity);

        AppUtils.transparentStatusBar(activity.getWindow());
        String[] texts = new String[]{
                "Experience More",
                "Experience Different",
                "Experience Conversation",
                "Experience Nerves",
                "Experience New",
                "Experience Passion",
                "Experience Happiness",
                "Experience Laughter",
                "Experience Unforgettable",
                "Experience Surprises" ,
                "Experience Fun",
                "Experience Emotions",
                "Experience Joy"};
        Random randomGenerator = new Random();
        int r = randomGenerator.nextInt(texts.length);
        randomText.setText(texts[r]);

        final Thread pBarThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (progress <= pBarMax) {
                        horizontal_progressBar.setProgress(progress);
                        sleep(15);
                        ++progress;
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        pBarThread.start();
    }

    public ProgressBar progressBar() {
        return horizontal_progressBar;
    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
