
package com.costar.talkwithidol.app.network.models.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mobile implements Parcelable
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
    private String verified;
    @SerializedName("tfa")
    @Expose
    private String tfa;
    public final static Creator<Mobile> CREATOR = new Creator<Mobile>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Mobile createFromParcel(Parcel in) {
            return new Mobile(in);
        }

        public Mobile[] newArray(int size) {
            return (new Mobile[size]);
        }

    }
    ;

    protected Mobile(Parcel in) {
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.localNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.verified = ((String) in.readValue((String.class.getClassLoader())));
        this.tfa = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Mobile() {
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

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getTfa() {
        return tfa;
    }

    public void setTfa(String tfa) {
        this.tfa = tfa;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(value);
        dest.writeValue(country);
        dest.writeValue(localNumber);
        dest.writeValue(verified);
        dest.writeValue(tfa);
    }

    public int describeContents() {
        return  0;
    }

}
