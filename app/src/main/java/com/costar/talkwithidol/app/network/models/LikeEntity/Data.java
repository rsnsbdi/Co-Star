
package com.costar.talkwithidol.app.network.models.LikeEntity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{



    @SerializedName("last_liker")
    @Expose
    private String last_liker;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total")
    @Expose
    private Integer total;
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
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.last_liker = ((String) in.readValue((String.class.getClassLoader())));

    }

    public Data() {
    }
    public String getLast_liker() {
        return last_liker;
    }

    public void setLast_liker(String last_liker) {
        this.last_liker = last_liker;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(total);
        dest.writeValue(last_liker);
    }

    public int describeContents() {
        return  0;
    }

}
