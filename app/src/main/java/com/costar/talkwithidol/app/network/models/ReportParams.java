package com.costar.talkwithidol.app.network.models;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class ReportParams implements Parcelable {


    public static ReportParams.Builder builder() {
        return new AutoValue_ReportParams.Builder();
    }

    public static TypeAdapter<ReportParams> typeAdapter(Gson gson) {
        return new AutoValue_ReportParams.GsonTypeAdapter(gson);
    }

    @SerializedName("category")
    public abstract String category();

    @SerializedName("type")
    public abstract String type();

    @SerializedName("id")
    public abstract int id();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder category(String category);

        public abstract Builder type(String type);

        public abstract Builder id(int id);


        public abstract ReportParams build();
    }
}
