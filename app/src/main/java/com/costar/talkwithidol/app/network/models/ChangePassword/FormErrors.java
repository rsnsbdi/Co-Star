
package com.costar.talkwithidol.app.network.models.ChangePassword;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormErrors implements Parcelable
{

    @SerializedName("pass")
    @Expose
    private String pass;
    public final static Creator<FormErrors> CREATOR = new Creator<FormErrors>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FormErrors createFromParcel(Parcel in) {
            return new FormErrors(in);
        }

        public FormErrors[] newArray(int size) {
            return (new FormErrors[size]);
        }

    }
    ;

    protected FormErrors(Parcel in) {
        this.pass = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FormErrors() {
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pass);
    }

    public int describeContents() {
        return  0;
    }

}
