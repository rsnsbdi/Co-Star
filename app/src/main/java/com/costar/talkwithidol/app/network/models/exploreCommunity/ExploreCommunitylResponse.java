
package com.costar.talkwithidol.app.network.models.exploreCommunity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExploreCommunitylResponse implements Parcelable
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
    private List<DatumC> data = null;

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @SerializedName("empty")
    @Expose
    private Boolean empty;
    public final static Creator<ExploreCommunitylResponse> CREATOR = new Creator<ExploreCommunitylResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ExploreCommunitylResponse createFromParcel(Parcel in) {
            ExploreCommunitylResponse instance = new ExploreCommunitylResponse();
            instance.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.empty = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

            instance.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.data, (DatumC.class.getClassLoader()));
            return instance;
        }

        public ExploreCommunitylResponse[] newArray(int size) {
            return (new ExploreCommunitylResponse[size]);
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

    public List<DatumC> getData() {
        return data;
    }

    public void setData(List<DatumC> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(error);
        dest.writeValue(empty);
        dest.writeValue(message);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
