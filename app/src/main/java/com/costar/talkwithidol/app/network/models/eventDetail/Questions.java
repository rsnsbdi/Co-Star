
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Questions implements Parcelable
{

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list")
    @Expose
    private List<QuestionList> questionList = null;
    public final static Creator<Questions> CREATOR = new Creator<Questions>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        public Questions[] newArray(int size) {
            return (new Questions[size]);
        }

    }
    ;

    protected Questions(Parcel in) {
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.questionList, (List.class.getClassLoader()));
    }

    public Questions() {
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

    public List<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(state);
        dest.writeValue(message);
        dest.writeList(questionList);
    }

    public int describeContents() {
        return  0;
    }

}
