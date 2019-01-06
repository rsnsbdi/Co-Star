
package com.costar.talkwithidol.app.network.models.ChangePassword;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse implements Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("deny")
    @Expose
    private Deny deny;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<ChangePasswordResponse> CREATOR = new Creator<ChangePasswordResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ChangePasswordResponse createFromParcel(Parcel in) {
            return new ChangePasswordResponse(in);
        }

        public ChangePasswordResponse[] newArray(int size) {
            return (new ChangePasswordResponse[size]);
        }

    }
    ;

    protected ChangePasswordResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.deny = ((Deny) in.readValue((Deny.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public ChangePasswordResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Deny getDeny() {
        return deny;
    }

    public void setDeny(Deny deny) {
        this.deny = deny;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(deny);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
