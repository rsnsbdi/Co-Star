
package com.costar.talkwithidol.app.network.models.login.LoginResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("sessid")
    @Expose
    private String sessid;
    @SerializedName("session_name")
    @Expose
    private String sessionName;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("reg_type")
    @Expose
    private String regType;
    @SerializedName("reg_source")
    @Expose
    private String regSource;
    @SerializedName("mobile_verified")
    @Expose
    private Boolean mobileVerified;
    @SerializedName("email_verified")
    @Expose
    private Boolean emailVerified;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("login")
    @Expose
    private Integer login;

    public String getEula_acceptance() {
        return eula_acceptance;
    }

    public void setEula_acceptance(String eula_acceptance) {
        this.eula_acceptance = eula_acceptance;
    }

    @SerializedName("eula_acceptance")
    @Expose
    private String eula_acceptance;
    @SerializedName("avatar_url")
@Expose
private String avatar_url;

    @SerializedName("created")
    @Expose
    private String created;

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    @SerializedName("profile_url")
    @Expose
    private String profile_url;

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

    }
    ;

    protected Data(Parcel in) {
        this.sessid = ((String) in.readValue((String.class.getClassLoader())));
        this.sessionName = ((String) in.readValue((String.class.getClassLoader())));
        this.token = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.reference = ((String) in.readValue((String.class.getClassLoader())));
        this.regType = ((String) in.readValue((String.class.getClassLoader())));
        this.regSource = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileVerified = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.emailVerified = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.mail = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.realname = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.login = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.avatar_url = ((String) in.readValue((String.class.getClassLoader())));
        this.eula_acceptance = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getCreated() {
        return created;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatarurl() {
        return avatar_url;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sessid);
        dest.writeValue(sessionName);
        dest.writeValue(token);
        dest.writeValue(uid);
        dest.writeValue(reference);
        dest.writeValue(regType);
        dest.writeValue(regSource);
        dest.writeValue(mobileVerified);
        dest.writeValue(emailVerified);
        dest.writeValue(mail);
        dest.writeValue(mobile);
        dest.writeValue(realname);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(country);
        dest.writeValue(login);
        dest.writeValue(created);
        dest.writeValue(avatar_url);
        dest.writeValue(eula_acceptance);
    }

    public int describeContents() {
        return  0;
    }

}
