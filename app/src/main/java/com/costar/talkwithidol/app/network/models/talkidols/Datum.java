
package com.costar.talkwithidol.app.network.models.talkidols;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("talent_name")
    @Expose
    private String talentName;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_state")
    @Expose
    private String eventState;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("weight")
    @Expose
    private Integer weight;


    public Boolean getMiddle() {
        return isMiddle;
    }

    public void setMiddle(Boolean middle) {
        isMiddle = middle;
    }

    @SerializedName("boolean")
    @Expose
    private Boolean isMiddle;


    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.talentName = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventName = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventId = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventState = ((String) in.readValue((String.class.getClassLoader())));
            instance.avatarUrl = ((String) in.readValue((Object.class.getClassLoader())));
            instance.weight = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    public String getTalentName() {
        return talentName;
    }

    public void setTalentName(String talentName) {
        this.talentName = talentName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(talentName);
        dest.writeValue(eventName);
        dest.writeValue(eventId);
        dest.writeValue(eventState);
        dest.writeValue(avatarUrl);
        dest.writeValue(weight);
    }

    public int describeContents() {
        return  0;
    }

}
