package com.costar.talkwithidol.app.network.models.ChangeEmail;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ChangeEmailParams {

    @SerializedName("current_pass")
    public abstract String currentPassword();

    @SerializedName("mail")
    public abstract String mail();






    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder currentPassword(String password);

        public abstract Builder mail(String mail);

        public abstract ChangeEmailParams build();
    }

    public static Builder builder() {
        return new AutoValue_ChangeEmailParams.Builder();
    }


    public static TypeAdapter<ChangeEmailParams> typeAdapter(Gson gson) {
        return new AutoValue_ChangeEmailParams.GsonTypeAdapter(gson);
    }
}
