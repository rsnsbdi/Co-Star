package com.costar.talkwithidol.app.network.models.forgotPassword;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class ForgotPasswordParams implements Parcelable {



    @SerializedName("name")
    public abstract String name();



    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder name(String name);


        public abstract ForgotPasswordParams build();
    }

    public static ForgotPasswordParams.Builder builder() {
        return new AutoValue_ForgotPasswordParams.Builder();
    }


    public static TypeAdapter<ForgotPasswordParams> typeAdapter(Gson gson) {
        return new AutoValue_ForgotPasswordParams.GsonTypeAdapter(gson);
    }
}
