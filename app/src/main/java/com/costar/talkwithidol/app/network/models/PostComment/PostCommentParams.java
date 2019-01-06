package com.costar.talkwithidol.app.network.models.PostComment;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class PostCommentParams implements Parcelable {



    @SerializedName("nid")
    public abstract String communityId();

    @SerializedName("pid")
    public abstract String parentId();


    @SerializedName("comment")
    public abstract String comment();





    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder communityId(String communityId);
        public abstract Builder parentId(String parentId);
        public abstract Builder comment(String comment);


        public abstract PostCommentParams build();
    }

    public static PostCommentParams.Builder builder() {
        return new AutoValue_PostCommentParams.Builder();
    }


    public static TypeAdapter<PostCommentParams> typeAdapter(Gson gson) {
        return new AutoValue_PostCommentParams.GsonTypeAdapter(gson);
    }
}
