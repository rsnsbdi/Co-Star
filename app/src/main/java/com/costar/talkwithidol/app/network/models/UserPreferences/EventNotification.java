
package com.costar.talkwithidol.app.network.models.UserPreferences;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventNotification implements Parcelable
{

    @SerializedName("top_level")
    @Expose
    private String topLevel;
    @SerializedName("new_events")
    @Expose
    private NewEvents newEvents;
    @SerializedName("closing_events")
    @Expose
    private ClosingEvents closingEvents;
    public final static Creator<EventNotification> CREATOR = new Creator<EventNotification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EventNotification createFromParcel(Parcel in) {
            return new EventNotification(in);
        }

        public EventNotification[] newArray(int size) {
            return (new EventNotification[size]);
        }

    }
    ;

    protected EventNotification(Parcel in) {
        this.topLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.newEvents = ((NewEvents) in.readValue((NewEvents.class.getClassLoader())));
        this.closingEvents = ((ClosingEvents) in.readValue((ClosingEvents.class.getClassLoader())));
    }

    public EventNotification() {
    }

    public String getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(String topLevel) {
        this.topLevel = topLevel;
    }

    public NewEvents getNewEvents() {
        return newEvents;
    }

    public void setNewEvents(NewEvents newEvents) {
        this.newEvents = newEvents;
    }

    public ClosingEvents getClosingEvents() {
        return closingEvents;
    }

    public void setClosingEvents(ClosingEvents closingEvents) {
        this.closingEvents = closingEvents;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(topLevel);
        dest.writeValue(newEvents);
        dest.writeValue(closingEvents);
    }

    public int describeContents() {
        return  0;
    }

}
