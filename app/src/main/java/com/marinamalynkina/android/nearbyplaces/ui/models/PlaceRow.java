package com.marinamalynkina.android.nearbyplaces.ui.models;

import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceCommonInfo;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */
@SuppressWarnings("serial")
public class PlaceRow implements Serializable {

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
}
