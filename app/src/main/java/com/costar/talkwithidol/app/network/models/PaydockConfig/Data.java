package com.costar.talkwithidol.app.network.models.PaydockConfig;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };
    @SerializedName("end_point")
    @Expose
    private String endpoint;
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("secret_key")
    @Expose
    private String secretKey;
    @SerializedName("gateway_id")
    @Expose
    private String gatewayId;
    protected Data(Parcel in) {
        this.environment = ((String) in.readValue((String.class.getClassLoader())));
        this.publicKey = ((String) in.readValue((String.class.getClassLoader())));
        this.secretKey = ((String) in.readValue((String.class.getClassLoader())));
        this.endpoint = ((String) in.readValue((String.class.getClassLoader())));
        this.gatewayId = ((String) in.readValue((String.class.getClassLoader())));
    }
    public Data() {
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(environment);
        dest.writeValue(publicKey);
        dest.writeValue(secretKey);
        dest.writeValue(gatewayId);
        dest.writeValue(endpoint);
    }

    public int describeContents() {
        return 0;
    }

}
