package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ilmarin on 11.10.16.
 */

public class PlaceFullInfo {

    @SerializedName("formatted_address")
    private String adress;

    @SerializedName("formatted_phone_number")
    private String phone;

    @SerializedName("international_phone_number")
    private String internationalPhone;

    private Geometry geomentry;

    private String icon;

    private String name;

    @SerializedName("place_id")
    private String placeId;

    private float rating;

    private ArrayList<String> types;

    private String url;

    private String website;

    public String getAdress() {
        return adress;
    }

    public Geometry getGeomentry() {
        return geomentry;
    }

    public String getIcon() {
        return icon;
    }

    public String getInternationalPhone() {
        return internationalPhone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPlaceId() {
        return placeId;
    }

    public float getRating() {
        return rating;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getUrl() {
        return url;
    }

    public String getWebsite() {
        return website;
    }
}
