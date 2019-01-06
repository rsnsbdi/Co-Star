
package com.costar.talkwithidol.app.network.models.SubscriptionStepOneResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaynowSumary implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cycle")
    @Expose
    private String cycle;
    @SerializedName("paynow_message")
    @Expose
    private String paynowMessage;
    @SerializedName("current_charge")
    @Expose
    private String currentCharge;
    public final static Creator<PaynowSumary> CREATOR = new Creator<PaynowSumary>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaynowSumary createFromParcel(Parcel in) {
            return new PaynowSumary(in);
        }

        public PaynowSumary[] newArray(int size) {
            return (new PaynowSumary[size]);
        }

    }
    ;

    protected PaynowSumary(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.cycle = ((String) in.readValue((String.class.getClassLoader())));
        this.paynowMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.currentCharge = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PaynowSumary() {
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

    public String getPaynowMessage() {
        return paynowMessage;
    }

    public void setPaynowMessage(String paynowMessage) {
        this.paynowMessage = paynowMessage;
    }

    public String getCurrentCharge() {
        return currentCharge;
    }

    public void setCurrentCharge(String currentCharge) {
        this.currentCharge = currentCharge;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(cycle);
        dest.writeValue(paynowMessage);
        dest.writeValue(currentCharge);
    }

    public int describeContents() {
        return  0;
    }

}
