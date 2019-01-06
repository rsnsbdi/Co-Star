package com.costar.talkwithidol.ui.fragments.channelvideos.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.adapters.VideoAdapter;
import com.costar.talkwithidol.ui.fragments.channelvideos.mvp.VideoModel;
import com.costar.talkwithidol.ui.fragments.channelvideos.mvp.VideoPresenter;
import com.costar.talkwithidol.ui.fragments.channelvideos.mvp.VideoView;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoModule {

    private final AppCompatActivity activity;

    public VideoModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @VideoScope
    @Provides
    public VideoView videoView(PreferencesManager preferencesManager, VideoAdapter videoAdapter) {
        return new VideoView(activity, preferencesManager, videoAdapter);
    }

    @VideoScope
    @Provides
    public VideoModel videoModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new VideoModel(activity, padloktNetwork, preferencesManager);
    }

    @VideoScope
    @Provides
    public VideoPresenter videoPresenter(VideoView videoView, VideoModel videoModel) {
        return new VideoPresenter(videoView, videoModel);
    }

    @VideoScope
    @Provides
    public VideoAdapter videoAdapter() {
        return new VideoAdapter();
    }
   /* @DiscoverScope
    @Provides
    public FitnessUtils fitnessUtils()
    {
        return new FitnessUtils(activity);
    }*/


}
