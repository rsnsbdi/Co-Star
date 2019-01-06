package com.costar.talkwithidol.app.network.models.register;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class VerificationParams {

    @SerializedName("number")
    public abstract String number();

    @SerializedName("country")
    public abstract String country();


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder number(String number);

        public abstract Builder country(String country);

        public abstract VerificationParams build();
    }

    public static Builder builder() {
        return new AutoValue_VerificationParams.Builder();
    }


    public static TypeAdapter<VerificationParams> typeAdapter(Gson gson) {
        return new AutoValue_VerificationParams.GsonTypeAdapter(gson);
    }
}
