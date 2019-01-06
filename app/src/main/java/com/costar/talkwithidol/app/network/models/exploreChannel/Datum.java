package com.costar.talkwithidol.app.network.models.exploreChannel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.diagonalColor = ((String) in.readValue((String.class.getClassLoader())));
            instance.channelId = ((String) in.readValue((String.class.getClassLoader())));
            instance.channelName = ((String) in.readValue((String.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.weight = ((String) in.readValue((String.class.getClassLoader())));

            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    };



    @SerializedName("diagonal_color")
    @Expose
    private String diagonalColor;
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("weight")
    @Expose
    private String weight;

    public String getDiagonalColor() {
        return diagonalColor;
    }

    public void setDiagonalColor(String diagonalColor) {
        this.diagonalColor = diagonalColor;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(channelId);
        dest.writeValue(channelName);
        dest.writeValue(diagonalColor);
        dest.writeValue(imageUrl);
        dest.writeValue(weight);
    }

    public int describeContents() {
        return 0;
    }

}
