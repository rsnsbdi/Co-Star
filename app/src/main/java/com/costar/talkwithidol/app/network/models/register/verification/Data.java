
package com.costar.talkwithidol.app.network.models.register.verification;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("verification_token")
    @Expose
    private String verificationToken;
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
        this.verificationToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(verificationToken);
    }

    public int describeContents() {
        return  0;
    }

}
