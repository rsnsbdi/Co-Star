package com.costar.talkwithidol.app.network.models.register;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RegisterParams {

    public static Builder builder() {
        return new AutoValue_RegisterParams.Builder();
    }

    public static TypeAdapter<RegisterParams> typeAdapter(Gson gson) {
        return new AutoValue_RegisterParams.GsonTypeAdapter(gson);
    }

    @SerializedName("name")
    public abstract String name();

    @SerializedName("mail")
    public abstract String mail();

    @SerializedName("pass")
    public abstract String pass();

    @SerializedName("full_name")
    public abstract String first_name();

    @Nullable
    @SerializedName("dob")
    public abstract String dob();

    @Nullable
    @SerializedName("gender")
    public abstract String gender();

    @Nullable
    @SerializedName("country")
    public abstract String country();

    @Nullable
    @SerializedName("timezone")
    public abstract String timezone();

    @Nullable
    @SerializedName("registration_source")
    public abstract String registration_source();

    @Nullable
    @SerializedName("verify_mobile")
    public abstract JsonObject verify_mobile();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder mail(String email);

        public abstract Builder pass(String pass);

        public abstract Builder first_name(String full_name);

        public abstract Builder dob(String dob);

        public abstract Builder gender(String gender);

        public abstract Builder country(String country);

        public abstract Builder timezone(String timezone);

        public abstract Builder registration_source(String registration_source);

        public abstract Builder verify_mobile(JsonObject verify_mobile);


        public abstract RegisterParams build();
    }
}
