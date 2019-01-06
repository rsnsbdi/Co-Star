
package com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingSummary implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cycle")
    @Expose
    private String cycle;
    @SerializedName("billing_message")
    @Expose
    private String billingMessage;
    @SerializedName("current_bill")
    @Expose
    private String currentBill;
    public final static Creator<BillingSummary> CREATOR = new Creator<BillingSummary>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BillingSummary createFromParcel(Parcel in) {
            return new BillingSummary(in);
        }

        public BillingSummary[] newArray(int size) {
            return (new BillingSummary[size]);
        }

    }
    ;

    protected BillingSummary(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.cycle = ((String) in.readValue((String.class.getClassLoader())));
        this.billingMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.currentBill = ((String) in.readValue((String.class.getClassLoader())));
    }

    public BillingSummary() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(cycle);
        dest.writeValue(billingMessage);
        dest.writeValue(currentBill);
    }

    public int describeContents() {
        return  0;
    }

}
