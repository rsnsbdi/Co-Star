
package com.costar.talkwithidol.app.network.models.UserChannelSubscription;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpiringChannel implements Parcelable
{

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
    public final static Creator<ExpiringChannel> CREATOR = new Creator<ExpiringChannel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ExpiringChannel createFromParcel(Parcel in) {
            return new ExpiringChannel(in);
        }

        public ExpiringChannel[] newArray(int size) {
            return (new ExpiringChannel[size]);
        }

    }
    ;

    protected ExpiringChannel(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.channelType = ((String) in.readValue((String.class.getClassLoader())));
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
        this.channelName = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.access = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ExpiringChannel() {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(channelType);
        dest.writeValue(channelId);
        dest.writeValue(channelName);
        dest.writeValue(shortDesc);
        dest.writeValue(imageUrl);
        dest.writeValue(access);
    }

    public int describeContents() {
        return  0;
    }

}
