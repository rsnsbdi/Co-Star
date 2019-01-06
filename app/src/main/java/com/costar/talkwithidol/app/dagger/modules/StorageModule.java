package com.costar.talkwithidol.app.dagger.modules;


import android.content.Context;


import com.costar.talkwithidol.app.dagger.AppScope;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @AppScope
    @Provides
    public PreferencesManager preferencesManager(Context context)
    {
        return new PreferencesManager(context);
    }
}
