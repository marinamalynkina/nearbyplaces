package com.marinamalynkina.android.nearbyplaces.data.network.retrofit;

import com.marinamalynkina.android.nearbyplaces.data.network.models.response.NearbyPlaces;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ilmarin on 11.10.16.
 */

public interface IGooglePlacesWebservicesAPI {

    @GET("api/place/nearbysearch/json?key=AIzaSyC0xzsgfDv-9UR1z-3uy77D_9Q3T2r6U6U")
    Call<NearbyPlaces> getNearbyPlaces(@Query("location") String location, @Query("radius") int radius);

    @GET("api/place/details/json?key=AIzaSyC0xzsgfDv-9UR1z-3uy77D_9Q3T2r6U6U")
    Call<PlaceInfoResponse> getPlaceInfo(@Query("placeid") String placeId);

}
