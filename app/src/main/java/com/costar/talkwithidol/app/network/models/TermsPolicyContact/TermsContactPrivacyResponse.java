
package com.costar.talkwithidol.app.network.models.TermsPolicyContact;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsContactPrivacyResponse implements Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("empty")
    @Expose
    private Boolean empty;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<TermsContactPrivacyResponse> CREATOR = new Creator<TermsContactPrivacyResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TermsContactPrivacyResponse createFromParcel(Parcel in) {
            return new TermsContactPrivacyResponse(in);
        }

        public TermsContactPrivacyResponse[] newArray(int size) {
            return (new TermsContactPrivacyResponse[size]);
        }

    }
    ;

    protected TermsContactPrivacyResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.empty = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public TermsContactPrivacyResponse() {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
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
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeValue(empty);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
