
package com.costar.talkwithidol.app.network.models.exploreNews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Likes implements Parcelable
{

    @SerializedName("user_like")
    @Expose
    private Boolean userLike;
    @SerializedName("last_liker")
    @Expose
    private Object lastLiker;
    @SerializedName("total_likes")
    @Expose
    private Integer totalLikes;
    public final static Creator<Likes> CREATOR = new Creator<Likes>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Likes createFromParcel(Parcel in) {
            Likes instance = new Likes();
            instance.userLike = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.lastLiker = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.totalLikes = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Likes[] newArray(int size) {
            return (new Likes[size]);
        }

    }
    ;

    public Boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }

    public Object getLastLiker() {
        return lastLiker;
    }

    public void setLastLiker(Object lastLiker) {
        this.lastLiker = lastLiker;
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userLike);
        dest.writeValue(lastLiker);
        dest.writeValue(totalLikes);
    }

    public int describeContents() {
        return  0;
    }

}
