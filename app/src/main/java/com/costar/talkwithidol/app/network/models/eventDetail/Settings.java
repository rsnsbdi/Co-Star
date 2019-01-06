package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings implements Parcelable {

    public final static Creator<Settings> CREATOR = new Creator<Settings>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        public Settings[] newArray(int size) {
            return (new Settings[size]);
        }

    };
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("questions")
    @Expose
    private Questions questions;

    @SerializedName("pexip")
    @Expose
    private Pexip pexip;
    @SerializedName("web_socket_address")
    @Expose
    private String web_socket_address;
    @SerializedName("web_socket_script")
    @Expose
    private String web_socket_script;
    @SerializedName("prelive_message")
    @Expose
    private String prelive_message;
    @SerializedName("censored_message")
    @Expose
    private String censored_message;
    @SerializedName("brightcove")
    @Expose
    private Brightcove brightcove;

    protected Settings(Parcel in) {
        this.mode = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.questions = ((Questions) in.readValue((Questions.class.getClassLoader())));
        this.brightcove = ((Brightcove) in.readValue((Brightcove.class.getClassLoader())));

        this.web_socket_address = ((String) in.readValue((String.class.getClassLoader())));
        this.web_socket_script = ((String) in.readValue((String.class.getClassLoader())));
        this.prelive_message = ((String) in.readValue((String.class.getClassLoader())));
        this.censored_message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Settings() {
    }

    public Pexip getPexip() {
        return pexip;
    }

    public void setPexip(Pexip pexip) {
        this.pexip = pexip;
    }

    public String getWeb_socket_address() {
        return web_socket_address;
    }

    public void setWeb_socket_address(String web_socket_address) {
        this.web_socket_address = web_socket_address;
    }

    public String getWeb_socket_script() {
        return web_socket_script;
    }

    public void setWeb_socket_script(String web_socket_script) {
        this.web_socket_script = web_socket_script;
    }

    public String getPrelive_message() {
        return prelive_message;
    }

    public void setPrelive_message(String prelive_message) {
        this.prelive_message = prelive_message;
    }

    public String getCensored_message() {
        return censored_message;
    }

    public void setCensored_message(String censored_message) {
        this.censored_message = censored_message;
    }

    public Brightcove getBrightcove() {
        return brightcove;
    }

    public void setBrightcove(Brightcove brightcove) {
        this.brightcove = brightcove;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mode);
        dest.writeValue(message);
        dest.writeValue(questions);
        dest.writeValue(brightcove);
        dest.writeValue(web_socket_address);
        dest.writeValue(web_socket_script);
        dest.writeValue(prelive_message);
        dest.writeValue(censored_message);
    }

    public int describeContents() {
        return 0;
    }

}
