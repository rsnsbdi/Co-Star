
package com.costar.talkwithidol.app.network.models.newsDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Access implements Parcelable
{

    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("granted")
    @Expose
    private Granted granted;
    public final static Creator<Access> CREATOR = new Creator<Access>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Access createFromParcel(Parcel in) {
            return new Access(in);
        }

        public Access[] newArray(int size) {
            return (new Access[size]);
        }

    }
    ;

    protected Access(Parcel in) {
        this.access = ((String) in.readValue((String.class.getClassLoader())));
        this.granted = ((Granted) in.readValue((Granted.class.getClassLoader())));
    }

    public Access() {
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Granted getGranted() {
        return granted;
    }

    public void setGranted(Granted granted) {
        this.granted = granted;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(access);
        dest.writeValue(granted);
    }

    public int describeContents() {
        return  0;
    }

}
