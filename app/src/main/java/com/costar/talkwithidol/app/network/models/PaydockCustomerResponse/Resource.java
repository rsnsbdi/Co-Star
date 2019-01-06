
package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<Resource> CREATOR = new Creator<Resource>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        public Resource[] newArray(int size) {
            return (new Resource[size]);
        }

    }
    ;

    protected Resource(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public Resource() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
