package com.costar.talkwithidol.app.network.models.addtowatchlist;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shreedhar on 10/12/17.
 */

@AutoValue
public abstract class AddToWatchlistParams implements Parcelable {



    @SerializedName("id")
    public abstract String id();

    @SerializedName("type")
    public abstract String type();

    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder id(String id);

        public abstract Builder type(String type);

        public abstract AddToWatchlistParams build();
    }

    public static AddToWatchlistParams.Builder builder() {
        return new AutoValue_AddToWatchlistParams.Builder();
    }


    public static TypeAdapter<AddToWatchlistParams> typeAdapter(Gson gson) {
        return new AutoValue_AddToWatchlistParams.GsonTypeAdapter(gson);
    }
}
