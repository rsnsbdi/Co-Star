
package com.costar.talkwithidol.app.network.models.exploreEvent;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumE implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("user_watchlist")
    @Expose
    private Boolean userWatchlist;
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

    @SerializedName("mode")
    @Expose
    private String mode;


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @SerializedName("channel_name")
    @Expose
    private String channelName;
    public final static Creator<DatumE> CREATOR = new Creator<DatumE>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumE createFromParcel(Parcel in) {
            return new DatumE(in);
        }

        public DatumE[] newArray(int size) {
            return (new DatumE[size]);
        }

    }
    ;

    protected DatumE(Parcel in) {
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));

        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.userWatchlist = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.eventId = ((String) in.readValue((String.class.getClassLoader())));
        this.eventType = ((String) in.readValue((String.class.getClassLoader())));
        this.eventState = ((String) in.readValue((String.class.getClassLoader())));
        this.eventName = ((String) in.readValue((String.class.getClassLoader())));
        this.eventTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.eventDate = ((EventDate) in.readValue((EventDate.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mode = ((String) in.readValue((String.class.getClassLoader())));

    }

    public DatumE() {
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

    public Boolean getUserWatchlist() {
        return userWatchlist;
    }

    public void setUserWatchlist(Boolean userWatchlist) {
        this.userWatchlist = userWatchlist;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(access);
        dest.writeValue(userWatchlist);
        dest.writeValue(eventId);
        dest.writeValue(eventType);
        dest.writeValue(eventState);
        dest.writeValue(eventName);
        dest.writeValue(eventTimestamp);
        dest.writeValue(eventDate);
        dest.writeValue(imageUrl);
        dest.writeValue(mode);
        dest.writeValue(channelName);
    }

    public int describeContents() {
        return  0;
    }

}
