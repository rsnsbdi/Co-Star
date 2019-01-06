package com.costar.talkwithidol.app.network.models.ChangeMobileResponse;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ChangeMobileParams {

    @SerializedName("number")
    public abstract String number();

    @SerializedName("country")
    public abstract String country();



    @SerializedName("verification_code")
    public abstract String verification_code();


    @SerializedName("verification_token")
    public abstract String verification_token();





    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder number(String number);

        public abstract Builder country(String country);
        public abstract Builder verification_code(String verification_code);
        public abstract Builder verification_token(String verification_token);

        public abstract ChangeMobileParams build();
    }

    public static Builder builder() {
        return new AutoValue_ChangeMobileParams.Builder();
    }


    public static TypeAdapter<ChangeMobileParams> typeAdapter(Gson gson) {
        return new AutoValue_ChangeMobileParams.GsonTypeAdapter(gson);
    }
}
