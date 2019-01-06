
package com.costar.talkwithidol.app.network.models.commentsResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildComment implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("parent_comment_id")
    @Expose
    private String parentCommentId;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("thread")
    @Expose
    private String thread;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("comment_timestamp")
    @Expose
    private String commentTimestamp;
    @SerializedName("comment_date")
    @Expose
    private String commentDate;
    @SerializedName("author_picture")
    @Expose
    private String authorPicture;

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    @SerializedName("author_id")
    @Expose
    private String author_id;


    public final static Creator<ChildComment> CREATOR = new Creator<ChildComment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ChildComment createFromParcel(Parcel in) {
            return new ChildComment(in);
        }

        public ChildComment[] newArray(int size) {
            return (new ChildComment[size]);
        }

    }
    ;

    protected ChildComment(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.commentId = ((String) in.readValue((String.class.getClassLoader())));
        this.parentCommentId = ((String) in.readValue((String.class.getClassLoader())));
        this.nodeId = ((String) in.readValue((String.class.getClassLoader())));
        this.thread = ((String) in.readValue((String.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.commentTimestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.commentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.authorPicture = ((String) in.readValue((String.class.getClassLoader())));
        this.author_id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ChildComment() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTimestamp() {
        return commentTimestamp;
    }

    public void setCommentTimestamp(String commentTimestamp) {
        this.commentTimestamp = commentTimestamp;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getAuthorPicture() {
        return authorPicture;
    }

    public void setAuthorPicture(String authorPicture) {
        this.authorPicture = authorPicture;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(commentId);
        dest.writeValue(parentCommentId);
        dest.writeValue(nodeId);
        dest.writeValue(thread);
        dest.writeValue(authorName);
        dest.writeValue(comment);
        dest.writeValue(commentTimestamp);
        dest.writeValue(commentDate);
        dest.writeValue(authorPicture);
        dest.writeValue(author_id);
    }

    public int describeContents() {
        return  0;
    }

}
