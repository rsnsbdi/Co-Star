
package com.costar.talkwithidol.app.network.models.exploreCommunity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments implements Parcelable
{

    @SerializedName("total_comments")
    @Expose
    private String totalComments;
    @SerializedName("last_comment")
    @Expose
    private LastComment lastComment;
    public final static Creator<Comments> CREATOR = new Creator<Comments>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Comments createFromParcel(Parcel in) {
            Comments instance = new Comments();
            instance.totalComments = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastComment = ((LastComment) in.readValue((LastComment.class.getClassLoader())));
            return instance;
        }

        public Comments[] newArray(int size) {
            return (new Comments[size]);
        }

    }
    ;

    public String getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(String totalComments) {
        this.totalComments = totalComments;
    }

    public LastComment getLastComment() {
        return lastComment;
    }

    public void setLastComment(LastComment lastComment) {
        this.lastComment = lastComment;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalComments);
        dest.writeValue(lastComment);
    }

    public int describeContents() {
        return  0;
    }

}
