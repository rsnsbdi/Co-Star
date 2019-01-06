
package com.costar.talkwithidol.app.network.models.notificationlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse implements Parcelable
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
    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @SerializedName("empty")
    @Expose
    private Boolean empty;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    public final static Creator<NotificationResponse> CREATOR = new Creator<NotificationResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationResponse createFromParcel(Parcel in) {
            return new NotificationResponse(in);
        }

        public NotificationResponse[] newArray(int size) {
            return (new NotificationResponse[size]);
        }

    }
    ;

    protected NotificationResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.empty = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (Datum.class.getClassLoader()));
    }

    public NotificationResponse() {
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(empty);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
