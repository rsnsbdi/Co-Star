
package com.costar.talkwithidol.app.network.models.UserPreferences;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatsOn implements Parcelable
{

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    public final static Creator<WhatsOn> CREATOR = new Creator<WhatsOn>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WhatsOn createFromParcel(Parcel in) {
            return new WhatsOn(in);
        }

        public WhatsOn[] newArray(int size) {
            return (new WhatsOn[size]);
        }

    }
    ;

    protected WhatsOn(Parcel in) {
        this.label = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.checked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public WhatsOn() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(label);
        dest.writeValue(text);
        dest.writeValue(checked);
    }

    public int describeContents() {
        return  0;
    }

}
