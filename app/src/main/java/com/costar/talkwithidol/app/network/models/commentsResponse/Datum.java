package com.costar.talkwithidol.app.network.models.commentsResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.costar.talkwithidol.app.network.models.communityDetail.Likes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable {

    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    };
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
    @SerializedName("child_comments_available")
    @Expose
    private Boolean childCommentsAvailable;
    @SerializedName("child_comments")
    @Expose
    private List<ChildComment> childComments = null;

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    @SerializedName("author_id")
    @Expose
    private String author_id;

    private Likes likes;
    private String description;
    private String imageUrl;

    protected Datum(Parcel in) {
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
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Likes) in.readValue((Likes.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.author_id = ((String) in.readValue((String.class.getClassLoader())));

        this.childCommentsAvailable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.childComments, (ChildComment.class.getClassLoader()));
    }

    public Datum() {
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Boolean getChildCommentsAvailable() {
        return childCommentsAvailable;
    }

    public void setChildCommentsAvailable(Boolean childCommentsAvailable) {
        this.childCommentsAvailable = childCommentsAvailable;
    }

    public List<ChildComment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<ChildComment> childComments) {
        this.childComments = childComments;
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
        dest.writeValue(childCommentsAvailable);
        dest.writeList(childComments);
        dest.writeValue(likes);
        dest.writeValue(imageUrl);
        dest.writeValue(description);
        dest.writeValue(author_id);
    }

    public int describeContents() {
        return 0;
    }

}
