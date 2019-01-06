
package com.costar.talkwithidol.app.network.models.newsDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("news_id")
    @Expose
    private String newsId;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("news_timestamp")
    @Expose
    private String newsTimestamp;
    @SerializedName("news_date")
    @Expose
    private String newsDate;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("granted")
    @Expose
    private Granted granted;
    @SerializedName("denied")
    @Expose
    private Denied denied;



    @SerializedName("channel_name")
    @Expose
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
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
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.newsId = ((String) in.readValue((String.class.getClassLoader())));
        this.newsName = ((String) in.readValue((String.class.getClassLoader())));
        this.newsTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.newsDate = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));

        this.granted = ((Granted) in.readValue((Granted.class.getClassLoader())));
        this.denied = ((Denied) in.readValue((Denied.class.getClassLoader())));
    }

    public Data() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsTimestamp() {
        return newsTimestamp;
    }

    public void setNewsTimestamp(String newsTimestamp) {
        this.newsTimestamp = newsTimestamp;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(access);
        dest.writeValue(newsId);
        dest.writeValue(newsName);
        dest.writeValue(newsTimestamp);
        dest.writeValue(newsDate);
        dest.writeValue(likes);
        dest.writeValue(imageUrl);
        dest.writeValue(description);
        dest.writeValue(granted);
        dest.writeValue(denied);
        dest.writeValue(channelName);
    }

    public int describeContents() {
        return  0;
    }

}
