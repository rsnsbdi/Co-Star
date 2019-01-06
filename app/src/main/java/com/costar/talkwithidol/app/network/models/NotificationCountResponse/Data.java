
package com.costar.talkwithidol.app.network.models.NotificationCountResponse;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("total")
    @Expose
    private String total;
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
        this.total = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
