
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brightcove implements Parcelable
{

    @SerializedName("refId")
    @Expose
    private String refId;
    @SerializedName("playerId")
    @Expose
    private String playerId;
    @SerializedName("policyKey")
    @Expose
    private String policyKey;
    @SerializedName("account")
    @Expose
    private String account;
    public final static Creator<Brightcove> CREATOR = new Creator<Brightcove>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Brightcove createFromParcel(Parcel in) {
            return new Brightcove(in);
        }

        public Brightcove[] newArray(int size) {
            return (new Brightcove[size]);
        }

    }
    ;

    protected Brightcove(Parcel in) {
        this.refId = ((String) in.readValue((String.class.getClassLoader())));
        this.playerId = ((String) in.readValue((String.class.getClassLoader())));
        this.policyKey = ((String) in.readValue((String.class.getClassLoader())));
        this.account = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Brightcove() {
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPolicyKey() {
        return policyKey;
    }

    public void setPolicyKey(String policyKey) {
        this.policyKey = policyKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(refId);
        dest.writeValue(playerId);
        dest.writeValue(policyKey);
        dest.writeValue(account);
    }

    public int describeContents() {
        return  0;
    }

}
