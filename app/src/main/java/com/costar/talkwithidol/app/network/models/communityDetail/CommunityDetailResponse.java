
package com.costar.talkwithidol.app.network.models.communityDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.costar.talkwithidol.app.network.models.commentsResponse.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityDetailResponse extends Datum implements Parcelable
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
    private Data data;
    public final static Creator<CommunityDetailResponse> CREATOR = new Creator<CommunityDetailResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CommunityDetailResponse createFromParcel(Parcel in) {
            return new CommunityDetailResponse(in);
        }

        public CommunityDetailResponse[] newArray(int size) {
            return (new CommunityDetailResponse[size]);
        }

    }
    ;

    protected CommunityDetailResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public CommunityDetailResponse() {
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
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
