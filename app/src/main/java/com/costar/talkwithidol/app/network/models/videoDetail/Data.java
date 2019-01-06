
package com.costar.talkwithidol.app.network.models.videoDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.costar.talkwithidol.app.network.models.eventDetail.Denied;

public class Data implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
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
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("granted")
    @Expose
    private Granted granted;
    @SerializedName("settings")
    @Expose
    private Settings settings;

    @SerializedName("denied")
    @Expose
    private Denied denied;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.videoId = ((String) in.readValue((String.class.getClassLoader())));
        this.videoName = ((String) in.readValue((String.class.getClassLoader())));
        this.talentName = ((String) in.readValue((String.class.getClassLoader())));
        this.videoTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.videoDate = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.denied = ((Denied) in.readValue((Denied.class.getClassLoader())));
        this.granted = ((Granted) in.readValue((Granted.class.getClassLoader())));
        this.settings = ((Settings) in.readValue((Settings.class.getClassLoader())));
    }

    public Data() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Granted getGranted() {
        return granted;
    }

    public void setGranted(Granted granted) {
        this.granted = granted;
    }

    public Denied getDenied() {
        return denied;
    }

    public void setDenied(Denied denied) {
        this.denied = denied;
    }
    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(videoId);
        dest.writeValue(videoName);
        dest.writeValue(talentName);
        dest.writeValue(videoTimestamp);
        dest.writeValue(videoDate);
        dest.writeValue(likes);
        dest.writeValue(imageUrl);
        dest.writeValue(description);
        dest.writeValue(access);
        dest.writeValue(denied);
        dest.writeValue(granted);
        dest.writeValue(settings);
    }

    public int describeContents() {
        return  0;
    }

}
