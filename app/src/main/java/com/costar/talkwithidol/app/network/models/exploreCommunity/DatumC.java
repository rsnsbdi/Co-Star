package com.costar.talkwithidol.app.network.models.exploreCommunity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumC implements Parcelable {

    public final static Creator<DatumC> CREATOR = new Creator<DatumC>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DatumC createFromParcel(Parcel in) {
            DatumC instance = new DatumC();
            instance.forumId = ((String) in.readValue((String.class.getClassLoader())));
            instance.forumName = ((String) in.readValue((String.class.getClassLoader())));
            instance.authorName = ((String) in.readValue((String.class.getClassLoader())));
            instance.channelName = ((String) in.readValue((String.class.getClassLoader())));
            instance.forumTimestamp = ((String) in.readValue((String.class.getClassLoader())));
            instance.forumDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
            instance.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
            instance.comments = ((Comments) in.readValue((Comments.class.getClassLoader())));
            instance.authorPicture = ((String) in.readValue((String.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.has_main_image = (Boolean) in.readValue((Boolean.class.getClassLoader()));

            return instance;
        }

        public DatumC[] newArray(int size) {
            return (new DatumC[size]);
        }

    };



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
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("author_picture")
    @Expose
    private String authorPicture;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
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

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(forumId);
        dest.writeValue(forumName);
        dest.writeValue(authorName);
        dest.writeValue(forumTimestamp);
        dest.writeValue(forumDate);
        dest.writeValue(shortDesc);
        dest.writeValue(likes);
        dest.writeValue(comments);
        dest.writeValue(authorPicture);
        dest.writeValue(imageUrl);
        dest.writeValue(channelName);
        dest.writeValue(has_main_image);
    }

    public int describeContents() {
        return 0;
    }

}
