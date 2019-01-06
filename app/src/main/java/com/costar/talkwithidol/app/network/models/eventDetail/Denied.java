
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Denied implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("freetrial")
    @Expose
    private Freetrial freetrial;
    @SerializedName("subscribe")
    @Expose
    private Subscribe subscribe;
    public final static Creator<Denied> CREATOR = new Creator<Denied>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Denied createFromParcel(Parcel in) {
            return new Denied(in);
        }

        public Denied[] newArray(int size) {
            return (new Denied[size]);
        }

    }
    ;

    protected Denied(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.freetrial = ((Freetrial) in.readValue((Freetrial.class.getClassLoader())));
        this.subscribe = ((Subscribe) in.readValue((Subscribe.class.getClassLoader())));
    }

    public Denied() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Freetrial getFreetrial() {
        return freetrial;
    }

    public void setFreetrial(Freetrial freetrial) {
        this.freetrial = freetrial;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(freetrial);
        dest.writeValue(subscribe);
    }

    public int describeContents() {
        return  0;
    }

}
