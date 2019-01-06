
package com.costar.talkwithidol.app.network.models.talkidols;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TalkIdolsResponse implements Parcelable
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
    private List<Datum> data = null;
    public final static Creator<TalkIdolsResponse> CREATOR = new Creator<TalkIdolsResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TalkIdolsResponse createFromParcel(Parcel in) {
            TalkIdolsResponse instance = new TalkIdolsResponse();
            instance.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.data, (Datum.class.getClassLoader()));
            return instance;
        }

        public TalkIdolsResponse[] newArray(int size) {
            return (new TalkIdolsResponse[size]);
        }

    }
    ;

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
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
