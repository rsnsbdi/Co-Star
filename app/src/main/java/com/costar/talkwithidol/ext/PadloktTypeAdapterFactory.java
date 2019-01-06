package com.costar.talkwithidol.ext;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by dell on 8/7/2017.
 */
@GsonTypeAdapterFactory
public abstract class PadloktTypeAdapterFactory implements TypeAdapterFactory {

    public  static TypeAdapterFactory create(){
        return new AutoValueGson_PadloktTypeAdapterFactory();
    }


}
