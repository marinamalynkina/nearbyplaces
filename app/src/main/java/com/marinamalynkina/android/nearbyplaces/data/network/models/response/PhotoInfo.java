package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */

public class PhotoInfo implements Parcelable {

    private int height;

    private int wifth;

    private String photo_reference;

    public PhotoInfo(int height, String photo_reference, int wifth) {
        this.height = height;
        this.photo_reference = photo_reference;
        this.wifth = wifth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWifth() {
        return wifth;
    }

    public void setWifth(int wifth) {
        this.wifth = wifth;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeInt(this.wifth);
        dest.writeString(this.photo_reference);
    }

    protected PhotoInfo(Parcel in) {
        this.height = in.readInt();
        this.wifth = in.readInt();
        this.photo_reference = in.readString();
    }

    public static final Parcelable.Creator<PhotoInfo> CREATOR = new Parcelable.Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
