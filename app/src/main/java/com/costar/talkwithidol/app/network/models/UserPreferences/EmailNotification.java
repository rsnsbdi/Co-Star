
package com.costar.talkwithidol.app.network.models.UserPreferences;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailNotification implements Parcelable
{

    @SerializedName("top_level")
    @Expose
    private String topLevel;
    @SerializedName("special_offers")
    @Expose
    private SpecialOffers specialOffers;
    @SerializedName("whats_on")
    @Expose
    private WhatsOn whatsOn;
    public final static Creator<EmailNotification> CREATOR = new Creator<EmailNotification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EmailNotification createFromParcel(Parcel in) {
            return new EmailNotification(in);
        }

        public EmailNotification[] newArray(int size) {
            return (new EmailNotification[size]);
        }

    }
    ;

    protected EmailNotification(Parcel in) {
        this.topLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.specialOffers = ((SpecialOffers) in.readValue((SpecialOffers.class.getClassLoader())));
        this.whatsOn = ((WhatsOn) in.readValue((WhatsOn.class.getClassLoader())));
    }

    public EmailNotification() {
    }

    public String getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(String topLevel) {
        this.topLevel = topLevel;
    }

    public SpecialOffers getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(SpecialOffers specialOffers) {
        this.specialOffers = specialOffers;
    }

    public WhatsOn getWhatsOn() {
        return whatsOn;
    }

    public void setWhatsOn(WhatsOn whatsOn) {
        this.whatsOn = whatsOn;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(topLevel);
        dest.writeValue(specialOffers);
        dest.writeValue(whatsOn);
    }

    public int describeContents() {
        return  0;
    }

}
