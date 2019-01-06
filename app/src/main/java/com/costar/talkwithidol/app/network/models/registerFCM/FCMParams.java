package com.costar.talkwithidol.app.network.models.registerFCM;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;



@AutoValue
public abstract class FCMParams implements Parcelable {



    @SerializedName("token")
    public abstract String token();

    @SerializedName("type")
    public abstract String type();

    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder token(String token);

        public abstract Builder type(String type);

        public abstract FCMParams build();
    }

    public static FCMParams.Builder builder() {
        return new AutoValue_FCMParams.Builder();
    }


    public static TypeAdapter<FCMParams> typeAdapter(Gson gson) {
        return new AutoValue_FCMParams.GsonTypeAdapter(gson);
    }
}
