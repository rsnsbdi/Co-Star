
package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaydockCustomerResponse implements Parcelable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("resource")
    @Expose
    private Resource resource;
    public final static Creator<PaydockCustomerResponse> CREATOR = new Creator<PaydockCustomerResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaydockCustomerResponse createFromParcel(Parcel in) {
            return new PaydockCustomerResponse(in);
        }

        public PaydockCustomerResponse[] newArray(int size) {
            return (new PaydockCustomerResponse[size]);
        }

    }
    ;

    protected PaydockCustomerResponse(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.error = ((Object) in.readValue((Object.class.getClassLoader())));
        this.resource = ((Resource) in.readValue((Resource.class.getClassLoader())));
    }

    public PaydockCustomerResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(error);
        dest.writeValue(resource);
    }

    public int describeContents() {
        return  0;
    }

}
