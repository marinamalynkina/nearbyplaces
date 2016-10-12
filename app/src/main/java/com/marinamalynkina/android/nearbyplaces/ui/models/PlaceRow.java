package com.marinamalynkina.android.nearbyplaces.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceCommonInfo;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */

public class PlaceRow implements Parcelable {

    private PlaceCommonInfo commonInfo;

    private String distance;

    public PlaceRow(PlaceCommonInfo commonInfo, String distance ) {
        this.distance = distance;
        this.commonInfo = commonInfo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public PlaceCommonInfo getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(PlaceCommonInfo commonInfo) {
        this.commonInfo = commonInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.commonInfo, flags);
        dest.writeString(this.distance);
    }

    protected PlaceRow(Parcel in) {
        this.commonInfo = in.readParcelable(PlaceCommonInfo.class.getClassLoader());
        this.distance = in.readString();
    }

    public static final Parcelable.Creator<PlaceRow> CREATOR = new Parcelable.Creator<PlaceRow>() {
        @Override
        public PlaceRow createFromParcel(Parcel source) {
            return new PlaceRow(source);
        }

        @Override
        public PlaceRow[] newArray(int size) {
            return new PlaceRow[size];
        }
    };
}
