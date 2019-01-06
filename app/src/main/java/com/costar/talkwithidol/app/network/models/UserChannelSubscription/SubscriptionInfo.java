package com.costar.talkwithidol.app.network.models.UserChannelSubscription;

import android.os.Parcel;
import android.os.Parcelable;

import com.costar.talkwithidol.app.network.models.SubscribedMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionInfo implements Parcelable {

    public final static Creator<SubscriptionInfo> CREATOR = new Creator<SubscriptionInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubscriptionInfo createFromParcel(Parcel in) {
            return new SubscriptionInfo(in);
        }

        public SubscriptionInfo[] newArray(int size) {
            return (new SubscriptionInfo[size]);
        }

    };
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("billing_message")
    @Expose
    private String billingMessage;
    @SerializedName("current_bill")
    @Expose
    private String currentBill;
    @SerializedName("cycle")
    @Expose
    private String cycle;
    @SerializedName("subscribed_gateway")
    @Expose
    private String subscribed_gateway;
    @SerializedName("subscribed_message")
    @Expose
    private SubscribedMessage subscribed_message;

    protected SubscriptionInfo(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.billingMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.currentBill = ((String) in.readValue((String.class.getClassLoader())));
        this.cycle = ((String) in.readValue((String.class.getClassLoader())));
        this.subscribed_gateway = ((String) in.readValue((String.class.getClassLoader())));
        this.subscribed_message = ((SubscribedMessage) in.readValue((String.class.getClassLoader())));
    }

    public SubscriptionInfo() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBillingMessage() {
        return billingMessage;
    }

    public void setBillingMessage(String billingMessage) {
        this.billingMessage = billingMessage;
    }

    public String getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(String currentBill) {
        this.currentBill = currentBill;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(billingMessage);
        dest.writeValue(currentBill);
        dest.writeValue(cycle);
        dest.writeValue(subscribed_gateway);
        dest.writeValue(subscribed_message);
    }

    public int describeContents() {
        return 0;
    }

}
