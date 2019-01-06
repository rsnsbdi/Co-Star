
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("denied")
    @Expose
    private Denied denied;
    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("user_watchlist")
    @Expose
    private Boolean user_watchlist;

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
        this.eventId = ((String) in.readValue((String.class.getClassLoader())));
        this.eventType = ((String) in.readValue((String.class.getClassLoader())));
        this.eventState = ((String) in.readValue((String.class.getClassLoader())));
        this.eventName = ((String) in.readValue((String.class.getClassLoader())));
        this.eventTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.eventDate = ((EventDate) in.readValue((EventDate.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.denied = ((Denied) in.readValue((Denied.class.getClassLoader())));
        this.settings = ((Settings) in.readValue((Settings.class.getClassLoader())));
        this.user_watchlist = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

    }

    public Data() {
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

    public Boolean  getUser_watchlist() {
        return user_watchlist;
    }

    public void setUser_watchlist(Boolean user_watchlist) {
        this.user_watchlist = user_watchlist;
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
        dest.writeValue(description);
        dest.writeValue(access);
        dest.writeValue(denied);
        dest.writeValue(settings);
        dest.writeValue(user_watchlist);
    }

    public int describeContents() {
        return  0;
    }

}
