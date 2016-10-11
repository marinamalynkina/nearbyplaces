package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ilmarin on 10.10.16.
 */
@SuppressWarnings("serial")
public class Location implements Serializable {
    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lng")
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

