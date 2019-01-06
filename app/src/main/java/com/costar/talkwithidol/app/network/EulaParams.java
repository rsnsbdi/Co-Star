package com.costar.talkwithidol.app.network;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class EulaParams implements Parcelable {


    public static EulaParams.Builder builder() {
        return new AutoValue_EulaParams.Builder();
    }

    public static TypeAdapter<EulaParams> typeAdapter(Gson gson) {
        return new AutoValue_EulaParams.GsonTypeAdapter(gson);
    }

    @SerializedName("acceptance")
    public abstract String acceptance();


    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder acceptance(String acceptance);

        public abstract EulaParams build();
    }
}
