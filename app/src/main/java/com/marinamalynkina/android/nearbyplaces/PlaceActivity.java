package com.marinamalynkina.android.nearbyplaces;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.NearbyPlaces;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceFullInfo;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceInfoResponse;
import com.marinamalynkina.android.nearbyplaces.data.network.retrofit.IGooglePlacesWebservicesAPI;
import com.marinamalynkina.android.nearbyplaces.ui.models.PlaceRow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceActivity extends AppCompatActivity {

    private PlaceRow placeRow;
    private PlaceFullInfo place;
    private TextView placeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MyLog.TAG, "onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeInfo = (TextView) findViewById(R.id.place_info);

        Intent i = getIntent();
        placeRow = (PlaceRow)i.getParcelableExtra("placeRow");

        if (placeRow != null) {
            Log.i(MyLog.TAG, "placeRow != null ");
        }

        build_retrofit_and_get_response();
    }


    private void build_retrofit_and_get_response() {

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGooglePlacesWebservicesAPI service = retrofit.create(IGooglePlacesWebservicesAPI.class);

        Call<PlaceInfoResponse> call = service.getPlaceInfo(placeRow.getCommonInfo().getPlaceId());

        call.enqueue(new Callback<PlaceInfoResponse>() {

            @Override
            public void onFailure(Call<PlaceInfoResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Log.i(MyLog.TAG, "restofit onFailure");
            }

            @Override
            public void onResponse(Call<PlaceInfoResponse> call, Response<PlaceInfoResponse> response) {
                Log.i(MyLog.TAG, "restofit onResponse");
                try {
                    Log.i(MyLog.TAG, "restofit onResponse response.message="+response.message());
                    Log.i(MyLog.TAG, "restofit onResponse response.code="+response.code());
                    Log.i(MyLog.TAG, "restofit onResponse response.isSuccessful="+response.isSuccessful());
                    Log.i(MyLog.TAG, "restofit onResponse response.status="+response.body().getStatus());
                    place = response.body().getPlace();
                    if (place != null) {
                        Log.i(MyLog.TAG," place not null" );
                                String info = place.getName() + "\n"
                                + place.getAdress() + "\n"
                                + place.getPhone() + "\n"
                                + place.getWebsite() + "\n"
                                + "rating " + place.getRating() + "\n";
                        placeInfo.setText(info);
                    }


                } catch (Exception e) {
                    Log.d(MyLog.TAG, "There is an error");
                    e.printStackTrace();
                }
            }
        });

    }
}
