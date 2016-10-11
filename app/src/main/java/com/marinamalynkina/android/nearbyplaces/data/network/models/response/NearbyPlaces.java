package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ilmarin on 10.10.16.
 */

public class NearbyPlaces {

    @SerializedName("results")
    private ArrayList<PlaceCommonInfo> places;

    private String status;

    @SerializedName("next_page_token")
    private String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public ArrayList<PlaceCommonInfo> getPlaces() {
        return places;
    }

    public String getStatus() {
        return status;
    }
}
