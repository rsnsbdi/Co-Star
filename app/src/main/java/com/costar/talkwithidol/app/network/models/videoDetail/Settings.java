
package com.costar.talkwithidol.app.network.models.videoDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings implements Parcelable
{

    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("brightcove")
    @Expose
    private Brightcove brightcove;
    public final static Creator<Settings> CREATOR = new Creator<Settings>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        public Settings[] newArray(int size) {
            return (new Settings[size]);
        }

    }
    ;

    protected Settings(Parcel in) {
        this.mode = ((String) in.readValue((String.class.getClassLoader())));
        this.brightcove = ((Brightcove) in.readValue((Brightcove.class.getClassLoader())));
    }

    public Settings() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Brightcove getBrightcove() {
        return brightcove;
    }

    public void setBrightcove(Brightcove brightcove) {
        this.brightcove = brightcove;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mode);
        dest.writeValue(brightcove);
    }

    public int describeContents() {
        return  0;
    }

}
