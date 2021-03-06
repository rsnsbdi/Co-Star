
package com.costar.talkwithidol.app.network.models.channelhome;

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
    private String lastLiker;
    @SerializedName("total_likes")
    @Expose
    private Integer totalLikes;
    public final static Creator<Likes> CREATOR = new Creator<Likes>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Likes createFromParcel(Parcel in) {
            return new Likes(in);
        }

        public Likes[] newArray(int size) {
            return (new Likes[size]);
        }

    }
    ;

    protected Likes(Parcel in) {
        this.userLike = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.lastLiker = ((String) in.readValue((String.class.getClassLoader())));
        this.totalLikes = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Likes() {
    }

    public Boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }

    public String getLastLiker() {
        return lastLiker;
    }

    public void setLastLiker(String lastLiker) {
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
