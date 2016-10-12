package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ilmarin on 10.10.16.
 */

public class PlaceCommonInfo implements Parcelable {

    private Geometry geometry;

    private String icon;

    private String name;

    @SerializedName("opening_hours")
    private OpeningHours openingHours;


    private ArrayList<PhotoInfo> photos;

    @SerializedName("place_id")
    private String placeId;

    private String scope;

    private String reference;

    private String vicinity;

    public PlaceCommonInfo(Geometry geometry, String icon, String name, OpeningHours openingHours, ArrayList<PhotoInfo> photos, String place_id, String reference, String scope, String vicinity) {
        this.geometry = geometry;
        this.icon = icon;
        this.name = name;
        this.openingHours = openingHours;
        this.photos = photos;
        this.placeId = place_id;
        this.reference = reference;
        this.scope = scope;
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public ArrayList<PhotoInfo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PhotoInfo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.geometry, flags);
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeParcelable(this.openingHours, flags);
        dest.writeTypedList(this.photos);
        dest.writeString(this.placeId);
        dest.writeString(this.scope);
        dest.writeString(this.reference);
        dest.writeString(this.vicinity);
    }

    protected PlaceCommonInfo(Parcel in) {
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.icon = in.readString();
        this.name = in.readString();
        this.openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        this.photos = in.createTypedArrayList(PhotoInfo.CREATOR);
        this.placeId = in.readString();
        this.scope = in.readString();
        this.reference = in.readString();
        this.vicinity = in.readString();
    }

    public static final Parcelable.Creator<PlaceCommonInfo> CREATOR = new Parcelable.Creator<PlaceCommonInfo>() {
        @Override
        public PlaceCommonInfo createFromParcel(Parcel source) {
            return new PlaceCommonInfo(source);
        }

        @Override
        public PlaceCommonInfo[] newArray(int size) {
            return new PlaceCommonInfo[size];
        }
    };
}
