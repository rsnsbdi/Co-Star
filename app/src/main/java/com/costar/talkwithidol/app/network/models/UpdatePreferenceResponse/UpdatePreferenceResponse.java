
package com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePreferenceResponse implements Parcelable
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
    @SerializedName("data")
    @Expose
    private Boolean data;
    public final static Creator<UpdatePreferenceResponse> CREATOR = new Creator<UpdatePreferenceResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UpdatePreferenceResponse createFromParcel(Parcel in) {
            return new UpdatePreferenceResponse(in);
        }

        public UpdatePreferenceResponse[] newArray(int size) {
            return (new UpdatePreferenceResponse[size]);
        }

    }
    ;

    protected UpdatePreferenceResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public UpdatePreferenceResponse() {
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

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
