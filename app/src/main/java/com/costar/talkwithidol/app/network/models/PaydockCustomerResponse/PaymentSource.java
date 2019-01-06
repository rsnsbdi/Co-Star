
package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSource implements Parcelable
{

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("vault_token")
    @Expose
    private String vaultToken;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("address_city")
    @Expose
    private String addressCity;
    @SerializedName("address_state")
    @Expose
    private String addressState;
    @SerializedName("address_country")
    @Expose
    private String addressCountry;
    @SerializedName("address_postcode")
    @Expose
    private String addressPostcode;
    @SerializedName("gateway_id")
    @Expose
    private String gatewayId;
    @SerializedName("card_name")
    @Expose
    private String cardName;
    @SerializedName("expire_month")
    @Expose
    private Integer expireMonth;
    @SerializedName("expire_year")
    @Expose
    private Integer expireYear;
    @SerializedName("card_number_last4")
    @Expose
    private String cardNumberLast4;
    @SerializedName("card_scheme")
    @Expose
    private String cardScheme;
    @SerializedName("ref_token")
    @Expose
    private String refToken;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    public final static Creator<PaymentSource> CREATOR = new Creator<PaymentSource>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaymentSource createFromParcel(Parcel in) {
            return new PaymentSource(in);
        }

        public PaymentSource[] newArray(int size) {
            return (new PaymentSource[size]);
        }

    }
    ;

    protected PaymentSource(Parcel in) {
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.vaultToken = ((String) in.readValue((String.class.getClassLoader())));
        this.addressLine1 = ((String) in.readValue((String.class.getClassLoader())));
        this.addressLine2 = ((String) in.readValue((String.class.getClassLoader())));
        this.addressCity = ((String) in.readValue((String.class.getClassLoader())));
        this.addressState = ((String) in.readValue((String.class.getClassLoader())));
        this.addressCountry = ((String) in.readValue((String.class.getClassLoader())));
        this.addressPostcode = ((String) in.readValue((String.class.getClassLoader())));
        this.gatewayId = ((String) in.readValue((String.class.getClassLoader())));
        this.cardName = ((String) in.readValue((String.class.getClassLoader())));
        this.expireMonth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.expireYear = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cardNumberLast4 = ((String) in.readValue((String.class.getClassLoader())));
        this.cardScheme = ((String) in.readValue((String.class.getClassLoader())));
        this.refToken = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PaymentSource() {
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVaultToken() {
        return vaultToken;
    }

    public void setVaultToken(String vaultToken) {
        this.vaultToken = vaultToken;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(Integer expireMonth) {
        this.expireMonth = expireMonth;
    }

    public Integer getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(Integer expireYear) {
        this.expireYear = expireYear;
    }

    public String getCardNumberLast4() {
        return cardNumberLast4;
    }

    public void setCardNumberLast4(String cardNumberLast4) {
        this.cardNumberLast4 = cardNumberLast4;
    }

    public String getCardScheme() {
        return cardScheme;
    }

    public void setCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
    }

    public String getRefToken() {
        return refToken;
    }

    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(updatedAt);
        dest.writeValue(vaultToken);
        dest.writeValue(addressLine1);
        dest.writeValue(addressLine2);
        dest.writeValue(addressCity);
        dest.writeValue(addressState);
        dest.writeValue(addressCountry);
        dest.writeValue(addressPostcode);
        dest.writeValue(gatewayId);
        dest.writeValue(cardName);
        dest.writeValue(expireMonth);
        dest.writeValue(expireYear);
        dest.writeValue(cardNumberLast4);
        dest.writeValue(cardScheme);
        dest.writeValue(refToken);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(id);
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
