
package com.costar.talkwithidol.app.network.models.exploreCommunity;

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
            LastComment instance = new LastComment();
            instance.comment = ((String) in.readValue((String.class.getClassLoader())));
            instance.authorName = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public LastComment[] newArray(int size) {
            return (new LastComment[size]);
        }

    }
    ;

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
