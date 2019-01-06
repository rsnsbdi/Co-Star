
package com.costar.talkwithidol.app.network.models.VerifyMobileResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("local_number")
    @Expose
    private String localNumber;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
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
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.localNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.verified = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Data() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(value);
        dest.writeValue(country);
        dest.writeValue(localNumber);
        dest.writeValue(verified);
    }

    public int describeContents() {
        return  0;
    }

}
