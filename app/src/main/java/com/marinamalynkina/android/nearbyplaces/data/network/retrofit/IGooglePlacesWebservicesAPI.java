package com.marinamalynkina.android.nearbyplaces.data.network.retrofit;

import com.marinamalynkina.android.nearbyplaces.data.network.models.response.NearbyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ilmarin on 11.10.16.
 */

public interface IGooglePlacesWebservicesAPI {




//    @Query(“key”) — for GET request query parameter
//    @QueryMap — for the map of parameters
//    @Body — use it with the @POST annotation to provide the query body content.


//    @GET("/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=YOUR_API_KEY")
//    Call<NearbyPlaces> nearbysearch(@Query("tagged") String tags);

    @GET("api/place/nearbysearch/json?key=AIzaSyC0xzsgfDv-9UR1z-3uy77D_9Q3T2r6U6U")
    Call<NearbyPlaces> getNearbyPlaces(@Query("location") String location, @Query("radius") int radius);



//    @GET("repos/{owner}/{repo}/contributors")
//    Call<List<Contributor>> repoContributors(
//            @Path("owner") String owner,
//            @Path("repo") String repo);
}
