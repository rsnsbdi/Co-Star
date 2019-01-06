package com.costar.talkwithidol.app.network.models.channelhome;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    };
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_type")
    @Expose
    private String eventType;
    @SerializedName("event_state")
    @Expose
    private String eventState;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_timestamp")
    @Expose
    private String eventTimestamp;
    @SerializedName("event_date")
    @Expose
    private EventDate eventDate;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
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
    @SerializedName("promo_id")
    @Expose
    private String promoId;
    @SerializedName("promo_name")
    @Expose
    private String promoName;
    @SerializedName("promo_content")
    @Expose
    private String promoContent;
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
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("author_picture")
    @Expose
    private String authorPicture;
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
    @SerializedName("promo_link")
    @Expose
    private String promo_link;
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
    @SerializedName("user_watchlist")
    @Expose
    private Boolean userWatchlist;


    protected Datum(Parcel in) {
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));

        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.eventId = ((String) in.readValue((String.class.getClassLoader())));
        this.eventType = ((String) in.readValue((String.class.getClassLoader())));
        this.eventState = ((String) in.readValue((String.class.getClassLoader())));
        this.eventName = ((String) in.readValue((String.class.getClassLoader())));
        this.eventTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.eventDate = ((EventDate) in.readValue((EventDate.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.videoId = ((String) in.readValue((String.class.getClassLoader())));
        this.videoName = ((String) in.readValue((String.class.getClassLoader())));
        this.talentName = ((String) in.readValue((String.class.getClassLoader())));
        this.videoTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.videoDate = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
        this.promoId = ((String) in.readValue((String.class.getClassLoader())));
        this.promoName = ((String) in.readValue((String.class.getClassLoader())));
        this.promoContent = ((String) in.readValue((String.class.getClassLoader())));
        this.forumId = ((String) in.readValue((String.class.getClassLoader())));
        this.forumName = ((String) in.readValue((String.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
        this.forumTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.forumDate = ((String) in.readValue((String.class.getClassLoader())));
        this.comments = ((Comments) in.readValue((Comments.class.getClassLoader())));
        this.authorPicture = ((String) in.readValue((String.class.getClassLoader())));
        this.newsId = ((String) in.readValue((String.class.getClassLoader())));
        this.newsName = ((String) in.readValue((String.class.getClassLoader())));
        this.newsTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.newsDate = ((String) in.readValue((String.class.getClassLoader())));
        this.has_main_image = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

    }

    public Datum() {
    }

    public String getChannelName() {
        return channelName;
    }

    public Boolean getUserWatchlist() {
        return userWatchlist;
    }

    public void setUserWatchlist(Boolean userWatchlist) {
        this.userWatchlist = userWatchlist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(String eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoContent() {
        return promoContent;
    }

    public void setPromoContent(String promoContent) {
        this.promoContent = promoContent;
    }

    public String getPromo_link() {
        return promo_link;
    }

    public void setPromo_link(String promo_link) {
        this.promo_link = promo_link;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(eventId);
        dest.writeValue(eventType);
        dest.writeValue(eventState);
        dest.writeValue(eventName);
        dest.writeValue(eventTimestamp);
        dest.writeValue(eventDate);
        dest.writeValue(imageUrl);
        dest.writeValue(videoId);
        dest.writeValue(videoName);
        dest.writeValue(talentName);
        dest.writeValue(videoTimestamp);
        dest.writeValue(videoDate);
        dest.writeValue(shortDesc);
        dest.writeValue(likes);
        dest.writeValue(promoId);
        dest.writeValue(promoName);
        dest.writeValue(promoContent);
        dest.writeValue(forumId);
        dest.writeValue(forumName);
        dest.writeValue(authorName);
        dest.writeValue(forumTimestamp);
        dest.writeValue(forumDate);
        dest.writeValue(comments);
        dest.writeValue(authorPicture);
        dest.writeValue(newsId);
        dest.writeValue(newsName);
        dest.writeValue(newsTimestamp);
        dest.writeValue(newsDate);
        dest.writeValue(channelName);
        dest.writeValue(has_main_image);
    }

    public int describeContents() {
        return 0;
    }

}
