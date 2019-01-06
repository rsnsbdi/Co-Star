package com.costar.talkwithidol.app.dagger;


import android.content.Context;

import com.costar.talkwithidol.app.dagger.modules.AppModule;
import com.costar.talkwithidol.app.dagger.modules.GsonModule;
import com.costar.talkwithidol.app.dagger.modules.NetworkModule;
import com.costar.talkwithidol.app.dagger.modules.StorageModule;
import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.squareup.picasso.Picasso;

import dagger.Component;
import okhttp3.OkHttpClient;

@AppScope
@Component(modules = {AppModule.class, GsonModule.class, NetworkModule.class, StorageModule.class})
public interface AppComponent {

    Context context();

    OkHttpClient okHttpClient();

    PadloktNetwork padloktNetwork();


    PreferencesManager preferencesManager();



    Picasso picasso();

}
