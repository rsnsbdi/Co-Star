package com.costar.talkwithidol.app.dagger.modules;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.costar.talkwithidol.app.dagger.AppScope;
import com.costar.talkwithidol.ext.PadloktTypeAdapterFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    @Provides
    @AppScope
    public Gson gson()
    {
        return Converters.registerAll(new GsonBuilder())
                .registerTypeAdapterFactory(PadloktTypeAdapterFactory.create())
                .create();
    }
}
