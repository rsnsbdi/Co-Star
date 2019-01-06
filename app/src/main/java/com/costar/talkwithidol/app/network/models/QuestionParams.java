package com.costar.talkwithidol.app.network.models;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;



@AutoValue
public abstract class QuestionParams implements Parcelable {



    @SerializedName("question")
    public abstract String question();



    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder question(String question);


        public abstract QuestionParams build();
    }

    public static QuestionParams.Builder builder() {
        return new AutoValue_QuestionParams.Builder();
    }


    public static TypeAdapter<QuestionParams> typeAdapter(Gson gson) {
        return new AutoValue_QuestionParams.GsonTypeAdapter(gson);
    }
}
