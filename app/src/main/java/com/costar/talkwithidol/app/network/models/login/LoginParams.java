package com.costar.talkwithidol.app.network.models.login;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class LoginParams implements Parcelable {



    @SerializedName("username")
    public abstract String username();

    @SerializedName("password")
    public abstract String password();

    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder username(String username);

        public abstract Builder password(String password);

        public abstract LoginParams build();
    }

    public static LoginParams.Builder builder() {
        return new AutoValue_LoginParams.Builder();
    }


    public static TypeAdapter<LoginParams> typeAdapter(Gson gson) {
        return new AutoValue_LoginParams.GsonTypeAdapter(gson);
    }
}
