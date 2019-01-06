package com.costar.talkwithidol.app;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

import com.costar.talkwithidol.BuildConfig;
import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.app.dagger.DaggerAppComponent;
import com.costar.talkwithidol.app.dagger.modules.AppModule;
import com.costar.talkwithidol.ext.Constants;

import timber.log.Timber;

/**
 * Created by dell on 8/7/2017.
 */

public class PadloktApplication extends Application{

    private AppComponent appComponent;


    ////
    @Override
    public void onCreate() {
        super.onCreate();
        initializeFonts();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    super.log(priority, Constants.LOG_TAG, message, t);
                }
            });
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();



    }



    public static PadloktApplication get(Activity activity) {
        return (PadloktApplication) activity.getApplication();
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    private void initializeFonts() {
        Fonts.PROXIBOLD = Typeface.createFromAsset(getAssets(), "ProximaNova-Bold.otf");
        Fonts.MOONBOLD = Typeface.createFromAsset(getAssets(),"MoonBold.otf");

    }

    public static final class Fonts {
        public static Typeface MOONBOLD;
        public static Typeface PROXIBOLD;

    }
}
