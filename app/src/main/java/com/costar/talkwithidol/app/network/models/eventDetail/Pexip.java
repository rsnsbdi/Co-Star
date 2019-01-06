
package com.costar.talkwithidol.app.network.models.eventDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pexip implements Parcelable
{

    @SerializedName("protocol")
    @Expose
    private String protocol;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("conference")
    @Expose
    private String conference;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("bandwidth")
    @Expose
    private String bandwidth;
    @SerializedName("web_socket_address")
    @Expose
    private String webSocketAddress;
    @SerializedName("web_socket_script")
    @Expose
    private String webSocketScript;
    public final static Creator<Pexip> CREATOR = new Creator<Pexip>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Pexip createFromParcel(Parcel in) {
            return new Pexip(in);
        }

        public Pexip[] newArray(int size) {
            return (new Pexip[size]);
        }

    }
    ;

    protected Pexip(Parcel in) {
        this.protocol = ((String) in.readValue((String.class.getClassLoader())));
        this.domain = ((String) in.readValue((String.class.getClassLoader())));
        this.destination = ((String) in.readValue((String.class.getClassLoader())));
        this.conference = ((String) in.readValue((String.class.getClassLoader())));
        this.pin = ((String) in.readValue((String.class.getClassLoader())));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.firstname = ((String) in.readValue((String.class.getClassLoader())));
        this.lastname = ((String) in.readValue((String.class.getClassLoader())));
        this.bandwidth = ((String) in.readValue((String.class.getClassLoader())));
        this.webSocketAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.webSocketScript = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Pexip() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getWebSocketAddress() {
        return webSocketAddress;
    }

    public void setWebSocketAddress(String webSocketAddress) {
        this.webSocketAddress = webSocketAddress;
    }

    public String getWebSocketScript() {
        return webSocketScript;
    }

    public void setWebSocketScript(String webSocketScript) {
        this.webSocketScript = webSocketScript;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(protocol);
        dest.writeValue(domain);
        dest.writeValue(destination);
        dest.writeValue(conference);
        dest.writeValue(pin);
        dest.writeValue(displayName);
        dest.writeValue(firstname);
        dest.writeValue(lastname);
        dest.writeValue(bandwidth);
        dest.writeValue(webSocketAddress);
        dest.writeValue(webSocketScript);
    }

    public int describeContents() {
        return  0;
    }

}
