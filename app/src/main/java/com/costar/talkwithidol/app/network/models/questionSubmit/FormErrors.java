
package com.costar.talkwithidol.app.network.models.questionSubmit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormErrors implements Parcelable
{

    @SerializedName("question")
    @Expose
    private String question;
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
        this.question = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FormErrors() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(question);
    }

    public int describeContents() {
        return  0;
    }

}
