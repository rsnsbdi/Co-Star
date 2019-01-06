
package com.costar.talkwithidol.app.network.models.channelhome;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastComment implements Parcelable
{

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    public final static Creator<LastComment> CREATOR = new Creator<LastComment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LastComment createFromParcel(Parcel in) {
            return new LastComment(in);
        }

        public LastComment[] newArray(int size) {
            return (new LastComment[size]);
        }

    }
    ;

    protected LastComment(Parcel in) {
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LastComment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(comment);
        dest.writeValue(authorName);
    }

    public int describeContents() {
        return  0;
    }

}
