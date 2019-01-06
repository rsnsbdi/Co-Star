package com.costar.talkwithidol.app.network.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dell on 11/5/2017.
 */
@AutoValue
public abstract class NtificationCountParam {

    @SerializedName("state")
    public abstract String state();



    @AutoValue.Builder
    public abstract static class Builder{

        public abstract NtificationCountParam.Builder state(String state);


        public abstract NtificationCountParam build();
    }

    public static NtificationCountParam.Builder builder() {
        return new AutoValue_NtificationCountParam.Builder();
    }


    public static TypeAdapter<NtificationCountParam> typeAdapter(Gson gson) {
        return new AutoValue_NtificationCountParam.GsonTypeAdapter(gson);
    }
}
