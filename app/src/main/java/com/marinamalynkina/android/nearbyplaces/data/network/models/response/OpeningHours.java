package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OpeningHours implements Parcelable {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekdayText = new ArrayList<Object>();


    public OpeningHours(Boolean openNow, List<Object> weekdayText) {
        this.openNow = openNow;
        this.weekdayText = weekdayText;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.openNow);
        dest.writeList(this.weekdayText);
    }

    protected OpeningHours(Parcel in) {
        this.openNow = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.weekdayText = new ArrayList<Object>();
        in.readList(this.weekdayText, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<OpeningHours> CREATOR = new Parcelable.Creator<OpeningHours>() {
        @Override
        public OpeningHours createFromParcel(Parcel source) {
            return new OpeningHours(source);
        }

        @Override
        public OpeningHours[] newArray(int size) {
            return new OpeningHours[size];
        }
    };
}