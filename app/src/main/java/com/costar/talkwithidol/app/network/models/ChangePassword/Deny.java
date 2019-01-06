
package com.costar.talkwithidol.app.network.models.ChangePassword;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deny implements Parcelable
{

    @SerializedName("form_errors")
    @Expose
    private FormErrors formErrors;
    public final static Creator<Deny> CREATOR = new Creator<Deny>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Deny createFromParcel(Parcel in) {
            return new Deny(in);
        }

        public Deny[] newArray(int size) {
            return (new Deny[size]);
        }

    }
    ;

    protected Deny(Parcel in) {
        this.formErrors = ((FormErrors) in.readValue((FormErrors.class.getClassLoader())));
    }

    public Deny() {
    }

    public FormErrors getFormErrors() {
        return formErrors;
    }

    public void setFormErrors(FormErrors formErrors) {
        this.formErrors = formErrors;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(formErrors);
    }

    public int describeContents() {
        return  0;
    }

}
