
package com.costar.talkwithidol.app.network.models.exploreNews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumN implements Parcelable
{

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
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("likes")
    @Expose
    private Likes likes;



    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    public final static Creator<DatumN> CREATOR = new Creator<DatumN>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumN createFromParcel(Parcel in) {
            DatumN instance = new DatumN();
            instance.newsId = ((String) in.readValue((String.class.getClassLoader())));
            instance.channelName = ((String) in.readValue((String.class.getClassLoader())));

            instance.newsName = ((String) in.readValue((String.class.getClassLoader())));
            instance.newsTimestamp = ((String) in.readValue((String.class.getClassLoader())));
            instance.newsDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
            instance.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));

            return instance;
        }

        public DatumN[] newArray(int size) {
            return (new DatumN[size]);
        }

    }
    ;

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
        dest.writeValue(newsId);
        dest.writeValue(newsName);
        dest.writeValue(newsTimestamp);
        dest.writeValue(newsDate);
        dest.writeValue(shortDesc);
        dest.writeValue(likes);
        dest.writeValue(imageUrl);
        dest.writeValue(channelName);
    }

    public int describeContents() {
        return  0;
    }

}
