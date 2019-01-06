package com.costar.talkwithidol.app.network.models.profile;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class UpdateProfileParams {

    public static Builder builder() {
        return new $AutoValue_UpdateProfileParams.Builder();
    }

    public static TypeAdapter<UpdateProfileParams> typeAdapter(Gson gson) {
        return new AutoValue_UpdateProfileParams.GsonTypeAdapter(gson);
    }

    @SerializedName("first_name")
    public abstract String first_name();

    @SerializedName("last_name")
    public abstract String last_name();

    @SerializedName("dob")
    public abstract String dob();

    @SerializedName("gender")
    public abstract String gender();
    @SerializedName("country")
    public abstract String country();

    @SerializedName("timezone")
    public abstract String timezone();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder first_name(String firstName);

        public abstract Builder country(String country);

        public abstract Builder last_name(String lastName);

        public abstract Builder dob(String dob);

        public abstract Builder gender(String gender);

        public abstract Builder timezone(String timezone);

        public abstract UpdateProfileParams build();
    }
}
