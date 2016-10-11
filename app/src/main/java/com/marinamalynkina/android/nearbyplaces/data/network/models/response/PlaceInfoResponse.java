package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ilmarin on 11.10.16.
 */

public class PlaceInfoResponse {

    @SerializedName("result")
    private PlaceFullInfo place;

    private String status;

    public PlaceFullInfo getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }
}
