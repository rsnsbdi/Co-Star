package com.costar.talkwithidol.app.network.models.communityDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.costar.talkwithidol.app.network.models.eventDetail.Denied;

public class Data implements Parcelable {

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

    };
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("forum_id")
    @Expose
    private String forumId;
    @SerializedName("forum_name")
    @Expose
    private String forumName;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("forum_timestamp")
    @Expose
    private String forumTimestamp;
    @SerializedName("forum_date")
    @Expose
    private String forumDate;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("author_picture")
    @Expose
    private String authorPicture;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("denied")
    @Expose
    private Denied denied;
    @SerializedName("granted")
    @Expose
    private Granted granted;


    @SerializedName("channel_name")
    @Expose
    private String channelName;


    public Boolean getHas_main_image() {
        return has_main_image;
    }

    public void setHas_main_image(Boolean has_main_image) {
        this.has_main_image = has_main_image;
    }

    @SerializedName("has_main_image")
    @Expose
    private Boolean has_main_image;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    protected Data(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.forumId = ((String) in.readValue((String.class.getClassLoader())));
        this.forumName = ((String) in.readValue((String.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
        this.forumTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.forumDate = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
        this.authorPicture = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));

        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.denied = ((Denied) in.readValue((Denied.class.getClassLoader())));

        this.granted = ((Granted) in.readValue((Granted.class.getClassLoader())));
        this.has_main_image = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Data() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getForumTimestamp() {
        return forumTimestamp;
    }

    public void setForumTimestamp(String forumTimestamp) {
        this.forumTimestamp = forumTimestamp;
    }

    public String getForumDate() {
        return forumDate;
    }

    public void setForumDate(String forumDate) {
        this.forumDate = forumDate;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public String getAuthorPicture() {
        return authorPicture;
    }

    public void setAuthorPicture(String authorPicture) {
        this.authorPicture = authorPicture;
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


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(forumId);
        dest.writeValue(forumName);
        dest.writeValue(authorName);
        dest.writeValue(forumTimestamp);
        dest.writeValue(forumDate);
        dest.writeValue(likes);
        dest.writeValue(authorPicture);
        dest.writeValue(imageUrl);
        dest.writeValue(description);
        dest.writeValue(access);
        dest.writeValue(denied);
        dest.writeValue(granted);
        dest.writeValue(channelName);
        dest.writeValue(has_main_image);
    }

    public int describeContents() {
        return 0;
    }

}
