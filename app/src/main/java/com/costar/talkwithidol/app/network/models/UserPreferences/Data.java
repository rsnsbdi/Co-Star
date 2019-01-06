
package com.costar.talkwithidol.app.network.models.UserPreferences;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("email_notification")
    @Expose
    private EmailNotification emailNotification;
    @SerializedName("event_notification")
    @Expose
    private EventNotification eventNotification;
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
        this.emailNotification = ((EmailNotification) in.readValue((EmailNotification.class.getClassLoader())));
        this.eventNotification = ((EventNotification) in.readValue((EventNotification.class.getClassLoader())));
    }

    public Data() {
    }

    public EmailNotification getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
    }

    public EventNotification getEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(EventNotification eventNotification) {
        this.eventNotification = eventNotification;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(emailNotification);
        dest.writeValue(eventNotification);
    }

    public int describeContents() {
        return  0;
    }

}
