package com.costar.talkwithidol.ui.fragments.newslist.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.adapters.NewsAdapterN;
import com.costar.talkwithidol.ui.fragments.newslist.mvp.NewsModel;
import com.costar.talkwithidol.ui.fragments.newslist.mvp.NewsPresenter;
import com.costar.talkwithidol.ui.fragments.newslist.mvp.NewsView;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsModule {

    private final AppCompatActivity activity;

    public NewsModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @NewsScope
    @Provides
    public NewsView newsView(PreferencesManager preferencesManager,NewsAdapterN newsAdapterN) {
        return new NewsView(activity, preferencesManager,newsAdapterN);
    }

    @NewsScope
    @Provides
    public NewsModel newsModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new NewsModel(activity, padloktNetwork, preferencesManager);
    }

    @NewsScope
    @Provides
    public NewsPresenter newsPresenter(NewsView newsView, NewsModel newsModel) {
        return new NewsPresenter(newsView, newsModel);
    }

    @NewsScope
    @Provides
    public NewsAdapterN newsAdapterN() {
        return new NewsAdapterN();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
