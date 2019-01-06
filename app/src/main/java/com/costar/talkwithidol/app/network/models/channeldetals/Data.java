package com.costar.talkwithidol.app.network.models.channeldetals;

import android.os.Parcel;
import android.os.Parcelable;

import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

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

    };
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("channel_type")
    @Expose
    private String channelType;
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("subscribed_gateway")
    @Expose
    private String subscribed_gateway;
    @SerializedName("subscribed_message")
    @Expose
    private SubscribedMessage subscribed_message;

    protected Data(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.channelType = ((String) in.readValue((String.class.getClassLoader())));
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.subscribed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subscribed_gateway = ((String) in.readValue((Boolean.class.getClassLoader())));
        this.subscribed_message = ((SubscribedMessage) in.readValue((Boolean.class.getClassLoader())));
    }

    public Data() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(channelType);
        dest.writeValue(channelId);
        dest.writeValue(channelName);
        dest.writeValue(shortDesc);
        dest.writeValue(imageUrl);
        dest.writeValue(access);
        dest.writeValue(description);
        dest.writeValue(subscribed);
        dest.writeValue(subscribed_gateway);
        dest.writeValue(subscribed_message);

    }

    public int describeContents() {
        return 0;
    }

}
