
package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics implements Parcelable
{

    @SerializedName("total_collected_amount")
    @Expose
    private Integer totalCollectedAmount;
    @SerializedName("successful_transactions")
    @Expose
    private Integer successfulTransactions;
    public final static Creator<Statistics> CREATOR = new Creator<Statistics>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Statistics createFromParcel(Parcel in) {
            return new Statistics(in);
        }

        public Statistics[] newArray(int size) {
            return (new Statistics[size]);
        }

    }
    ;

    protected Statistics(Parcel in) {
        this.totalCollectedAmount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.successfulTransactions = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Statistics() {
    }

    public Integer getTotalCollectedAmount() {
        return totalCollectedAmount;
    }

    public void setTotalCollectedAmount(Integer totalCollectedAmount) {
        this.totalCollectedAmount = totalCollectedAmount;
    }

    public Integer getSuccessfulTransactions() {
        return successfulTransactions;
    }

    public void setSuccessfulTransactions(Integer successfulTransactions) {
        this.successfulTransactions = successfulTransactions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalCollectedAmount);
        dest.writeValue(successfulTransactions);
    }

    public int describeContents() {
        return  0;
    }

}
