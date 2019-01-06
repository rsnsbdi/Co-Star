
package com.costar.talkwithidol.app.network.models.questionSubmit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("form_errors")
    @Expose
    private FormErrors formErrors;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(Parcel in) {
        this.qid = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.formErrors = ((FormErrors) in.readValue((FormErrors.class.getClassLoader())));
    }

    public Data() {
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FormErrors getFormErrors() {
        return formErrors;
    }

    public void setFormErrors(FormErrors formErrors) {
        this.formErrors = formErrors;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(qid);
        dest.writeValue(state);
        dest.writeValue(message);
        dest.writeValue(formErrors);
    }

    public int describeContents() {
        return  0;
    }

}
