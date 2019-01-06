package com.costar.talkwithidol.app.network.models.newsDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscribe implements Parcelable {

    public final static Creator<Subscribe> CREATOR = new Creator<Subscribe>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Subscribe createFromParcel(Parcel in) {
            return new Subscribe(in);
        }

        public Subscribe[] newArray(int size) {
            return (new Subscribe[size]);
        }

    };
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subscribed_gateway")
    @Expose
    private String subscribed_gateway;
    @SerializedName("subscribed_message")
    @Expose
    private SubscribedMessage subscribed_message;

    protected Subscribe(Parcel in) {
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.subscribed_gateway = ((String) in.readValue((String.class.getClassLoader())));
        this.subscribed_message = ((SubscribedMessage) in.readValue((String.class.getClassLoader())));
    }

    public Subscribe() {
    }

    public String getSubscribed_gateway() {
        return subscribed_gateway;
    }

    public void setSubscribed_gateway(String subscribed_gateway) {
        this.subscribed_gateway = subscribed_gateway;
    }

    public SubscribedMessage getSubscribed_message() {
        return subscribed_message;
    }

    public void setSubscribed_message(SubscribedMessage subscribed_message) {
        this.subscribed_message = subscribed_message;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(channelId);
        dest.writeValue(code);
        dest.writeValue(name);
        dest.writeValue(message);
        dest.writeValue(subscribed_message);
        dest.writeValue(subscribed_gateway);
    }

    public int describeContents() {
        return 0;
    }

}
