
package com.costar.talkwithidol.app.network.models.promo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("promo_id")
    @Expose
    private String promoId;
    @SerializedName("promo_section")
    @Expose
    private String promoSection;
    @SerializedName("promo_name")
    @Expose
    private String promoName;
    @SerializedName("promo_content")
    @Expose
    private String promoContent;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;


    @SerializedName("promo_link")
    @Expose
    private String promo_link;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.promoId = ((String) in.readValue((String.class.getClassLoader())));
            instance.promoSection = ((String) in.readValue((String.class.getClassLoader())));
            instance.promoName = ((String) in.readValue((String.class.getClassLoader())));
            instance.promoContent = ((String) in.readValue((String.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.promo_link = ((String) in.readValue((String.class.getClassLoader())));

            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    public String getpromo_link() {
        return promo_link;
    }

    public void setpromo_link(String promo_link) {
        this.promo_link = promo_link;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getPromoSection() {
        return promoSection;
    }

    public void setPromoSection(String promoSection) {
        this.promoSection = promoSection;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoContent() {
        return promoContent;
    }

    public void setPromoContent(String promoContent) {
        this.promoContent = promoContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(promoId);
        dest.writeValue(promoSection);
        dest.writeValue(promoName);
        dest.writeValue(promoContent);
        dest.writeValue(imageUrl);
        dest.writeValue(promo_link);
    }

    public int describeContents() {
        return  0;
    }

}
