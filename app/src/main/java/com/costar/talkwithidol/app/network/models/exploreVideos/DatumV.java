
package com.costar.talkwithidol.app.network.models.exploreVideos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumV implements Parcelable
{

    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("video_name")
    @Expose
    private String videoName;
    @SerializedName("talent_name")
    @Expose
    private String talentName;
    @SerializedName("video_timestamp")
    @Expose
    private String videoTimestamp;
    @SerializedName("video_date")
    @Expose
    private String videoDate;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    public final static Creator<DatumV> CREATOR = new Creator<DatumV>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumV createFromParcel(Parcel in) {
            DatumV instance = new DatumV();
            instance.videoId = ((String) in.readValue((String.class.getClassLoader())));
            instance.videoName = ((String) in.readValue((String.class.getClassLoader())));
            instance.talentName = ((String) in.readValue((String.class.getClassLoader())));
            instance.videoTimestamp = ((String) in.readValue((String.class.getClassLoader())));
            instance.videoDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.shortDesc = ((String) in.readValue((Object.class.getClassLoader())));
            instance.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DatumV[] newArray(int size) {
            return (new DatumV[size]);
        }

    }
    ;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getTalentName() {
        return talentName;
    }

    public void setTalentName(String talentName) {
        this.talentName = talentName;
    }

    public String getVideoTimestamp() {
        return videoTimestamp;
    }

    public void setVideoTimestamp(String videoTimestamp) {
        this.videoTimestamp = videoTimestamp;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(videoId);
        dest.writeValue(videoName);
        dest.writeValue(talentName);
        dest.writeValue(videoTimestamp);
        dest.writeValue(videoDate);
        dest.writeValue(shortDesc);
        dest.writeValue(likes);
        dest.writeValue(imageUrl);
    }

    public int describeContents() {
        return  0;
    }

}
