
package com.costar.talkwithidol.app.network.models.addtowatchlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.costar.talkwithidol.app.network.models.LikeEntity.Data;

public class AddToWatchlistResponse implements Parcelable
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
    private com.costar.talkwithidol.app.network.models.LikeEntity.Data data;
    public final static Creator<AddToWatchlistResponse> CREATOR = new Creator<AddToWatchlistResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddToWatchlistResponse createFromParcel(Parcel in) {
            return new AddToWatchlistResponse(in);
        }

        public AddToWatchlistResponse[] newArray(int size) {
            return (new AddToWatchlistResponse[size]);
        }

    }
    ;

    protected AddToWatchlistResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((com.costar.talkwithidol.app.network.models.LikeEntity.Data) in.readValue((com.costar.talkwithidol.app.network.models.LikeEntity.Data.class.getClassLoader())));
    }

    public AddToWatchlistResponse() {
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

    public com.costar.talkwithidol.app.network.models.LikeEntity.Data getData() {
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
