package com.costar.talkwithidol.app.network.models.LikeEntity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shreedhar on 10/12/17.
 */

@AutoValue
public abstract class LikeEntityParams implements Parcelable {



    @SerializedName("id")
    public abstract String id();

    @SerializedName("type")
    public abstract String type();

    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder id(String id);

        public abstract Builder type(String type);

        public abstract LikeEntityParams build();
    }

    public static LikeEntityParams.Builder builder() {
        return new AutoValue_LikeEntityParams.Builder();
    }


    public static TypeAdapter<LikeEntityParams> typeAdapter(Gson gson) {
        return new AutoValue_LikeEntityParams.GsonTypeAdapter(gson);
    }
}
