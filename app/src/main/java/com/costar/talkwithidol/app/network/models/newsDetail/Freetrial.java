
package com.costar.talkwithidol.app.network.models.newsDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Freetrial implements Parcelable
{

    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Freetrial> CREATOR = new Creator<Freetrial>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Freetrial createFromParcel(Parcel in) {
            return new Freetrial(in);
        }

        public Freetrial[] newArray(int size) {
            return (new Freetrial[size]);
        }

    }
    ;

    protected Freetrial(Parcel in) {
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Freetrial() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(channelId);
        dest.writeValue(code);
        dest.writeValue(name);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
