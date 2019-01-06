
package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service implements Parcelable
{

    @SerializedName("default_gateway_id")
    @Expose
    private String defaultGatewayId;
    public final static Creator<Service> CREATOR = new Creator<Service>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        public Service[] newArray(int size) {
            return (new Service[size]);
        }

    }
    ;

    protected Service(Parcel in) {
        this.defaultGatewayId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Service() {
    }

    public String getDefaultGatewayId() {
        return defaultGatewayId;
    }

    public void setDefaultGatewayId(String defaultGatewayId) {
        this.defaultGatewayId = defaultGatewayId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(defaultGatewayId);
    }

    public int describeContents() {
        return  0;
    }

}
