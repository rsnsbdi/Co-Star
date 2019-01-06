package com.costar.talkwithidol.app.dagger.modules;


import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.costar.talkwithidol.app.dagger.AppScope;
import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class NetworkModule {


    @AppScope
    @Provides
    public Cache cache(Context context)
    {
        return new Cache(new File(context.getCacheDir(), Constants.HTTP_DIR_CACHE),Constants.CACHE_SIZE);
    }


    @AppScope
    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    @AppScope
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Cache cache)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
               // .addInterceptor(new HeaderInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }


    @AppScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson)
    {
        return new Retrofit.Builder().client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }



    @AppScope
    @Provides
    public PadloktNetwork eWellNetwork(Retrofit retrofit)
    {
        return retrofit.create(PadloktNetwork.class);
    }




    @AppScope
    @Provides
    public Picasso picasso(Context context, OkHttpClient okHttpClient)
    {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

}
