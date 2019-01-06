
package com.costar.talkwithidol.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscribedMessage implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("link")
    @Expose
    private String link;
    public final static Creator<SubscribedMessage> CREATOR = new Creator<SubscribedMessage>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SubscribedMessage createFromParcel(Parcel in) {
            return new SubscribedMessage(in);
        }

        public SubscribedMessage[] newArray(int size) {
            return (new SubscribedMessage[size]);
        }

    }
    ;

    protected SubscribedMessage(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.body = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SubscribedMessage() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(body);
        dest.writeValue(link);
    }

    public int describeContents() {
        return  0;
    }

}
