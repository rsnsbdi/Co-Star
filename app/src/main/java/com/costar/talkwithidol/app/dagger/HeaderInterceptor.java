package com.costar.talkwithidol.app.dagger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 9/17/2017.
 */

public class HeaderInterceptor
        implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Cookie", "SSESS65daf66746e4e426c61f32c3d72341d9=d2TXHdrp86z_6BjTUwiOr4l4kEVH7OXVQaqUo08yDBc")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}