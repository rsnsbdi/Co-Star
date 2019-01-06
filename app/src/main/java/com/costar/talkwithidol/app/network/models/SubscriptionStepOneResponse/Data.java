
package com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("subscription")
    @Expose
    private String subscription;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("billing_summary")
    @Expose
    private BillingSummary billingSummary;
    @SerializedName("paynow_sumary")
    @Expose
    private PaynowSumary paynowSumary;
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
        this.subscription = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.billingSummary = ((BillingSummary) in.readValue((BillingSummary.class.getClassLoader())));
        this.paynowSumary = ((PaynowSumary) in.readValue((PaynowSumary.class.getClassLoader())));
    }

    public Data() {
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BillingSummary getBillingSummary() {
        return billingSummary;
    }

    public void setBillingSummary(BillingSummary billingSummary) {
        this.billingSummary = billingSummary;
    }

    public PaynowSumary getPaynowSumary() {
        return paynowSumary;
    }

    public void setPaynowSumary(PaynowSumary paynowSumary) {
        this.paynowSumary = paynowSumary;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(subscription);
        dest.writeValue(message);
        dest.writeValue(billingSummary);
        dest.writeValue(paynowSumary);
    }

    public int describeContents() {
        return  0;
    }

}
