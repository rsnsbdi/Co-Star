
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList implements Parcelable
{

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    public final static Creator<QuestionList> CREATOR = new Creator<QuestionList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public QuestionList createFromParcel(Parcel in) {
            return new QuestionList(in);
        }

        public QuestionList[] newArray(int size) {
            return (new QuestionList[size]);
        }

    }
    ;

    protected QuestionList(Parcel in) {
        this.qid = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.question = ((String) in.readValue((String.class.getClassLoader())));
        this.weight = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public QuestionList() {
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(qid);
        dest.writeValue(uid);
        dest.writeValue(question);
        dest.writeValue(weight);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
    }

    public int describeContents() {
        return  0;
    }

}
