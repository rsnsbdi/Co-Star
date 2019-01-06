
package com.costar.talkwithidol.app.network.models.TermsPolicyContact;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String tite;
    @SerializedName("description")
    @Expose
    private String description;
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
        this.tite = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getTite() {
        return tite;
    }

    public void setTite(String tite) {
        this.tite = tite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tite);
        dest.writeValue(description);
    }

    public int describeContents() {
        return  0;
    }

}
