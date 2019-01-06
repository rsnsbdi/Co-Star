
package com.costar.talkwithidol.app.network.models.NotificationSeen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("notify_id")
    @Expose
    private String notifyId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("link")
    @Expose
    private Link link;
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
        this.notifyId = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((Link) in.readValue((Link.class.getClassLoader())));
    }

    public Data() {
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(notifyId);
        dest.writeValue(message);
        dest.writeValue(state);
        dest.writeValue(imageUrl);
        dest.writeValue(link);
    }

    public int describeContents() {
        return  0;
    }

}
