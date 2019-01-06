package com.costar.talkwithidol.ui.activities.splash.mvp;


import android.content.Intent;

import com.costar.talkwithidol.EventPretestActivity;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.GCM.Configs;
import com.costar.talkwithidol.ui.activities.communitydetail.CommunityDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.costar.talkwithidol.ui.activities.eventdetaillive.EventDetailLiveActivity;
import com.costar.talkwithidol.ui.activities.newsdetail.NewsDtailActivity;
import com.costar.talkwithidol.ui.activities.splash.mvp.views.SplashView;
import com.costar.talkwithidol.ui.activities.videodetail.VideoDtailActivity;
import com.costar.talkwithidol.ui.fragments.channeldetailactivity.ChannelDetailFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SplashPresenter {

    private final SplashView splashView;
    private final SplashModel splashModel;
    DisposableObserver<Boolean> loginObserver;
    PreferencesManager preferencesManager;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public SplashPresenter(SplashView splashView, SplashModel splashModel, PreferencesManager preferencesManager) {
        this.splashView = splashView;
        this.splashModel = splashModel;
        this.preferencesManager = preferencesManager;

    }

    public void onResume() {
        loginObserver = loginObserver();
        start3SecondSleep();
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }


    private DisposableObserver<Boolean> loginObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {

                if (splashView.sharedPreferencesN.getString(Constants.ISFIRSTLAUNCH, null) == null) {
                    splashModel.startTutorial();

                } else {
                    normalFlow();
                }

            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Timber.e("Completed");

            }
        };
    }

    private void normalFlow() {
        if (splashView.activity.getIntent().getStringExtra(Configs.TYPE) == null) {

            if (splashModel.getData(Constants.TOKEN) == null) {

                splashModel.startLogin();

            } else {

                splashModel.startDashboard();

            }

        } else {

            if (splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("video")) {
                splashModel.startDashboard();
                Intent intent = new Intent(splashView.activity, VideoDtailActivity.class);
                intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                splashView.activity.startActivity(intent);
                splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

            } else if (splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("community")) {
                splashModel.startDashboard();
                Intent intent = new Intent(splashView.activity, CommunityDtailActivity.class);
                intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                splashView.activity.startActivity(intent);
                splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

            } else if (splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("news")) {
                splashModel.startDashboard();
                Intent intent = new Intent(splashView.activity, NewsDtailActivity.class);
                intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                splashView.activity.startActivity(intent);
                splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

            } else if (splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("channel")) {
                splashModel.startDashboard();
                Intent intent = new Intent(splashView.activity, ChannelDetailFragment.class);
                intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                splashView.activity.startActivity(intent);
                splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

            } else if (splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("event") || splashView.activity.getIntent().getStringExtra(Configs.TYPE).equalsIgnoreCase("events")) {

                if (splashView.activity.getIntent().getStringExtra(Configs.MODE) != null) {

                    if (splashView.activity.getIntent().getStringExtra(Configs.MODE).equalsIgnoreCase("upcoming")) {
                        splashModel.startDashboard();
                        Intent intent = new Intent(splashView.activity, EventDtailActivity.class);
                        intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                        splashView.activity.startActivity(intent);
                        splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

                    } else if (splashView.activity.getIntent().getStringExtra(Configs.MODE).equalsIgnoreCase("watch_live")) {
                        splashModel.startDashboard();
                        Intent intent = new Intent(splashView.activity, EventDetailLiveActivity.class);
                        intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                        splashView.activity.startActivity(intent);
                        preferencesManager.save(Configs.TYPE, null);

                    } else if (splashView.activity.getIntent().getStringExtra(Configs.MODE).equalsIgnoreCase("talent_vmr")) {
                        splashModel.startDashboard();
                        Intent intent = new Intent(splashView.activity, EventPretestActivity.class);
                        intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                        splashView.activity.startActivity(intent);
                        preferencesManager.save(Configs.TYPE, null);

                    } else if (splashView.activity.getIntent().getStringExtra(Configs.MODE).equalsIgnoreCase("participent_vmr")) {
                        splashModel.startDashboard();
                        Intent intent = new Intent(splashView.activity, EventPretestActivity.class);
                        intent.putExtra("VIDEOID", splashView.activity.getIntent().getStringExtra(Configs.ID));
                        splashView.activity.startActivity(intent);
                        splashView.sharedPreferencesN.edit().putString(Configs.TYPE, null).apply();

                    }
                }
            }

        }
    }

    private void sleepFor3Seconds() {

        try {

            Thread.sleep(1500);

        } catch (InterruptedException e) {
            Timber.d("Operation was interrupted");
        }


    }

    private Observable<Boolean> getSleepObservable() {
        return Observable.just(true)
                .map(aBoolean -> {
                    sleepFor3Seconds();
                    return aBoolean;
                });
    }

    private void start3SecondSleep() {
        getSleepObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginObserver);
    }

}
