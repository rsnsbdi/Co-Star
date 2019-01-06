package com.costar.talkwithidol.app.network.models.ChangePassword;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class ChangePasswordParams implements Parcelable {



    @SerializedName("current_pass")
    public abstract String currentPassword();


    @SerializedName("pass")
    public abstract String password();



    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder currentPassword(String currentpass);

        public abstract Builder password(String pass);


        public abstract ChangePasswordParams build();
    }

    public static ChangePasswordParams.Builder builder() {
        return new AutoValue_ChangePasswordParams.Builder();
    }


    public static TypeAdapter<ChangePasswordParams> typeAdapter(Gson gson) {
        return new AutoValue_ChangePasswordParams.GsonTypeAdapter(gson);
    }
}
