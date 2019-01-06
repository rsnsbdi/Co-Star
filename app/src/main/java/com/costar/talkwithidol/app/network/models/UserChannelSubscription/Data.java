
package com.costar.talkwithidol.app.network.models.UserChannelSubscription;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable
{

    @SerializedName("subscription_info")
    @Expose
    private SubscriptionInfo subscriptionInfo;
    @SerializedName("subscribed_channels")
    @Expose
    private List<SubscribedChannel> subscribedChannels = null;
    @SerializedName("expiring_channels")
    @Expose
    private List<ExpiringChannel> expiringChannels = null;
    @SerializedName("unsubscribed_channels")
    @Expose
    private List<UnsubscribedChannel> unsubscribedChannels = null;
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
        this.subscriptionInfo = ((SubscriptionInfo) in.readValue((SubscriptionInfo.class.getClassLoader())));
        in.readList(this.subscribedChannels, (SubscribedChannel.class.getClassLoader()));
        in.readList(this.expiringChannels, (ExpiringChannel.class.getClassLoader()));
        in.readList(this.unsubscribedChannels, (UnsubscribedChannel.class.getClassLoader()));
    }

    public Data() {
    }

    public SubscriptionInfo getSubscriptionInfo() {
        return subscriptionInfo;
    }

    public void setSubscriptionInfo(SubscriptionInfo subscriptionInfo) {
        this.subscriptionInfo = subscriptionInfo;
    }

    public List<SubscribedChannel> getSubscribedChannels() {
        return subscribedChannels;
    }

    public void setSubscribedChannels(List<SubscribedChannel> subscribedChannels) {
        this.subscribedChannels = subscribedChannels;
    }

    public List<ExpiringChannel> getExpiringChannels() {
        return expiringChannels;
    }

    public void setExpiringChannels(List<ExpiringChannel> expiringChannels) {
        this.expiringChannels = expiringChannels;
    }

    public List<UnsubscribedChannel> getUnsubscribedChannels() {
        return unsubscribedChannels;
    }

    public void setUnsubscribedChannels(List<UnsubscribedChannel> unsubscribedChannels) {
        this.unsubscribedChannels = unsubscribedChannels;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(subscriptionInfo);
        dest.writeList(subscribedChannels);
        dest.writeList(expiringChannels);
        dest.writeList(unsubscribedChannels);
    }

    public int describeContents() {
        return  0;
    }

}
