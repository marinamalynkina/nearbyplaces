package com.marinamalynkina.android.nearbyplaces.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.marinamalynkina.android.nearbyplaces.App;
import com.marinamalynkina.android.nearbyplaces.MyLog;
import com.marinamalynkina.android.nearbyplaces.R;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceFullInfo;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceInfoResponse;
import com.marinamalynkina.android.nearbyplaces.data.network.retrofit.IGooglePlacesWebservicesAPI;
import com.marinamalynkina.android.nearbyplaces.ui.models.PlaceRow;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceActivity extends AppCompatActivity {

    private PlaceRow placeRow;
    private PlaceFullInfo place;
    private TextView placeInfo;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MyLog.TAG, "onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ((App) getApplication()).getNetComponent().inject(this);

        placeInfo = (TextView) findViewById(R.id.place_info);

        Intent i = getIntent();
        placeRow = (PlaceRow)i.getParcelableExtra("placeRow");

        if (placeRow != null) {
            Log.i(MyLog.TAG, "placeRow != null ");
        }

        retrofitRequest();
    }

    private void retrofitRequest() {
        //Create a retrofit call object
        Call<PlaceInfoResponse> call = retrofit.create(IGooglePlacesWebservicesAPI.class).getPlaceInfo(placeRow.getCommonInfo().getPlaceId());
        //Enque the call
        call.enqueue(new Callback<PlaceInfoResponse>() {
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

            @Override
            public void onFailure(Call<PlaceInfoResponse> call, Throwable t) {
                Log.i(MyLog.TAG, "restofit onFailure "+ t.toString());
            }
        });
    }
}
