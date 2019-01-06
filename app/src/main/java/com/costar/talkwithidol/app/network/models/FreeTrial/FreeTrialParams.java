package com.costar.talkwithidol.app.network.models.FreeTrial;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class FreeTrialParams {


    @SerializedName("channel_id")
    public abstract String id();


    @AutoValue.Builder
    public abstract static class Builder {


        public abstract Builder id(String id);

        public abstract FreeTrialParams build();
    }

    public static Builder builder() {
        return new AutoValue_FreeTrialParams.Builder();
    }


    public static TypeAdapter<FreeTrialParams> typeAdapter(Gson gson) {
        return new AutoValue_FreeTrialParams.GsonTypeAdapter(gson);
    }
}
