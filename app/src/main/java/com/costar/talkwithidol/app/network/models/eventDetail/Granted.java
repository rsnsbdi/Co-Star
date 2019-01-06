
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Granted implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Granted> CREATOR = new Creator<Granted>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Granted createFromParcel(Parcel in) {
            return new Granted(in);
        }

        public Granted[] newArray(int size) {
            return (new Granted[size]);
        }

    }
    ;

    protected Granted(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Granted() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
