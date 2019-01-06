
package com.costar.talkwithidol.app.network.models.exploreEvent;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDate implements Parcelable
{

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("minute")
    @Expose
    private String minute;
    @SerializedName("second")
    @Expose
    private Integer second;
    @SerializedName("fraction")
    @Expose
    private Integer fraction;
    @SerializedName("warning_count")
    @Expose
    private Integer warningCount;
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = null;
    @SerializedName("error_count")
    @Expose
    private Integer errorCount;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("is_localtime")
    @Expose
    private Boolean isLocaltime;
    public final static Creator<EventDate> CREATOR = new Creator<EventDate>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EventDate createFromParcel(Parcel in) {
            return new EventDate(in);
        }

        public EventDate[] newArray(int size) {
            return (new EventDate[size]);
        }

    }
    ;

    protected EventDate(Parcel in) {
        this.year = ((String) in.readValue((Integer.class.getClassLoader())));
        this.month = ((String) in.readValue((Integer.class.getClassLoader())));
        this.day = ((String) in.readValue((Integer.class.getClassLoader())));
        this.hour = ((String) in.readValue((Integer.class.getClassLoader())));
        this.minute = ((String) in.readValue((Integer.class.getClassLoader())));
        this.second = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.fraction = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.warningCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.warnings, (Object.class.getClassLoader()));
        this.errorCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.errors, (Object.class.getClassLoader()));
        this.isLocaltime = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public EventDate() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getFraction() {
        return fraction;
    }

    public void setFraction(Integer fraction) {
        this.fraction = fraction;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public Boolean getIsLocaltime() {
        return isLocaltime;
    }

    public void setIsLocaltime(Boolean isLocaltime) {
        this.isLocaltime = isLocaltime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(year);
        dest.writeValue(month);
        dest.writeValue(day);
        dest.writeValue(hour);
        dest.writeValue(minute);
        dest.writeValue(second);
        dest.writeValue(fraction);
        dest.writeValue(warningCount);
        dest.writeList(warnings);
        dest.writeValue(errorCount);
        dest.writeList(errors);
        dest.writeValue(isLocaltime);
    }

    public int describeContents() {
        return  0;
    }

}
