package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ilmarin on 10.10.16.
 */
@SuppressWarnings("serial")
public class PlaceCommonInfo implements Serializable {

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
}
