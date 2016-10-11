package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilmarin on 10.10.16.
 */

public class Location {
    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lng")
    private Double longitude;
}
