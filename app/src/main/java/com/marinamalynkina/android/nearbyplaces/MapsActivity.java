package com.marinamalynkina.android.nearbyplaces;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.*;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.NearbyPlaces;
import com.marinamalynkina.android.nearbyplaces.data.network.models.response.PlaceCommonInfo;
import com.marinamalynkina.android.nearbyplaces.data.network.retrofit.IGooglePlacesWebservicesAPI;
import com.marinamalynkina.android.nearbyplaces.ui.PlacesListAdapter;
import com.marinamalynkina.android.nearbyplaces.ui.models.PlaceRow;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        OnConnectionFailedListener,
        LocationListener,
        ConnectionCallbacks,
        ActivityCompat.OnRequestPermissionsResultCallback,
        ResultCallback<LocationSettingsResult>,
        PlacesListAdapter.PlaceListListener{

    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private static final int defaultZoomLevel = 15;
    private static final int defaultRadius = 500;
    private static final int minChangeDistanceForUpdate = 100;

    // Keys for storing activity state in the Bundle.
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";

    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    protected final static String KEY_CURRENTLOCATION = "current_location";

    // location with last requested places
    private Location mLastLocation;
    protected final static String KEY_LASTLOCATION = "last_location";

    private boolean isMapLoaded;
    private Marker mCurrLocationMarker;


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;




    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    private ArrayList<PlaceRow> placesList = new ArrayList<>();
    protected final static String KEY_PLACES = "places";
    private RecyclerView recyclerView;
    private PlacesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MyLog.TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // получение вью нижнего экрана
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // настройка поведения нижнего экрана
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // настройка состояний нижнего экрана
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // настройка максимальной высоты
        bottomSheetBehavior.setPeekHeight(getResources().getDimensionPixelOffset(R.dimen.bottom_sheet_height));

        // настройка возможности скрыть элемент при свайпе вниз
        bottomSheetBehavior.setHideable(false);

        // настройка колбэков при изменениях
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.places);

        mAdapter = new PlacesListAdapter(placesList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkLocationPermission(this);
        }

        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d(MyLog.TAG, "oncreate Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d(MyLog.TAG, "oncreate Google Play Services available. Continuing.");
        }

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        // Create an instance of GoogleAPIClient.
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        isMapLoaded = false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(MyLog.TAG, "updateValuesFromBundle");
        if (savedInstanceState != null) {
            Log.i(MyLog.TAG, "updateValuesFromBundle savedInstanceState not null");
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_CURRENTLOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_CURRENTLOCATION);
                if (mCurrentLocation != null)
                    Log.i(MyLog.TAG, "updateValuesFromBundle mCurrentLocation "+ mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude());
            }

            if (savedInstanceState.keySet().contains(KEY_LASTLOCATION)) {
                mLastLocation = savedInstanceState.getParcelable(KEY_LASTLOCATION);
                if (mLastLocation != null)
                Log.i(MyLog.TAG, "updateValuesFromBundle mLastLocation "+ mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }

            if (savedInstanceState.keySet().contains(KEY_PLACES)) {
                placesList = savedInstanceState.getParcelableArrayList(KEY_PLACES);
                Log.i(MyLog.TAG, "updateValuesFromBundle placesList exist "+ placesList.size());

            }
//            savedInstanceState.putParcelableArrayList(KEY_PLACES, placesList);
        }
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} method, with the results provided through a {@code PendingResult}.
     */
    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    /**
     * The callback invoked when
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} is called. Examines the
     * {@link com.google.android.gms.location.LocationSettingsResult} object and determines if
     * location settings are adequate. If they are not, begins the process of presenting a location
     * settings dialog to the user.
     */
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(MyLog.TAG, "All location settings are satisfied.");
//                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(MyLog.TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(MyLog.TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(MyLog.TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(MyLog.TAG, "onMapReady");
        mMap = googleMap;
        // Enable the my location layer if the permission has been granted.
        if (PermissionUtils.isLocationPermissionEnable(this))
            initializeMapsSettings();


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public void initializeMapsSettings() {
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(MyLog.TAG, "onConnectionFailed");
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(MyLog.TAG, "onConnected");

        setPlacesOnMap();

        if (PermissionUtils.checkLocationPermission(this)) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
//            setLocationonMap(mLastLocation);

//            setMyLocation(mLastLocation);

//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            startLocationUpdates();


        }

    }

    private void setLocationonMap(Location location) {
        if (location != null) {
            mCurrentLocation = location;
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_LONG).show();
            Log.i(MyLog.TAG, latitude + " " + longitude);

            LatLng position = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(position).title("current position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f));
        }
    }

    private void setMyLocation(@Nullable Location location) {
        if (location != null) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(defaultZoomLevel));
        }
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
//        if (PermissionUtils.isLocationPermissionEnable(this)) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.i(MyLog.TAG, "onConnectionSuspended");
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }

    private void build_retrofit_and_get_response() {

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGooglePlacesWebservicesAPI service = retrofit.create(IGooglePlacesWebservicesAPI.class);

        Log.i(MyLog.TAG, "location "+ mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());

        Call<NearbyPlaces> call = service.getNearbyPlaces(mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude(), defaultRadius);

        call.enqueue(new Callback<NearbyPlaces>() {

            @Override
            public void onFailure(Call<NearbyPlaces> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Log.i(MyLog.TAG, "restofit onFailure");
            }

            @Override
            public void onResponse(Call<NearbyPlaces> call, Response<NearbyPlaces> response) {
                Log.i(MyLog.TAG, "restofit onResponse");
                try {

                    Log.i(MyLog.TAG, "restofit onResponse response.message="+response.message());
                    Log.i(MyLog.TAG, "restofit onResponse response.code="+response.code());
                    Log.i(MyLog.TAG, "restofit onResponse response.isSuccessful="+response.isSuccessful());
                    Log.i(MyLog.TAG, "restofit onResponse response.status="+response.body().getStatus());
                    Log.i(MyLog.TAG, "restofit onResponse response.places size="+response.body().getPlaces().size());


                    List<PlaceRow> list = new ArrayList<PlaceRow>();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getPlaces().size(); i++) {
                        Double lat = response.body().getPlaces().get(i).getGeometry().getLocation().getLatitude();
                        Double lng = response.body().getPlaces().get(i).getGeometry().getLocation().getLongitude();

                        LatLng latLng = new LatLng(lat, lng);

                        Location l = new Location(mCurrentLocation);
                        l.setLatitude(lat);
                        l.setLongitude(lng);

                        int distance = Math.round(mCurrentLocation.distanceTo(l));

                        // Adding Marker to the Camera.
                        if (distance <= defaultRadius) {
                            list.add(new PlaceRow(response.body().getPlaces().get(i), distance + " m"));
                        }

                    }

                    placesList.clear();
                    placesList.addAll(list);

                    setPlacesOnMap();
                } catch (Exception e) {
                    Log.d(MyLog.TAG, "There is an error");
                    e.printStackTrace();
                }
            }
        });

    }

    public void setPlacesOnMap() {
        if (placesList != null && placesList.size() > 0) {
            mMap.clear();

            for (int i = 0; i < placesList.size(); i++) {
                Double lat = placesList.get(i).getCommonInfo().getGeometry().getLocation().getLatitude();
                Double lng = placesList.get(i).getCommonInfo().getGeometry().getLocation().getLongitude();
                String placeName = placesList.get(i).getCommonInfo().getName();
                String vicinity = placesList.get(i).getCommonInfo().getVicinity();

                Log.i(MyLog.TAG, "place  " + i + " " + lat + "," + lng + " " + placeName + " " + vicinity);


                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(lat, lng);

                // Position of Marker on Map
                markerOptions.position(latLng);
                // Adding Title to the Marker
                markerOptions.title(placeName + " " + placesList.get(i).getDistance() + " м");
                // Adding Marker to the Camera.
                Marker m = mMap.addMarker(markerOptions);
                // Adding colour to the marker
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                // move map camera
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        mMap.animateCamera(CameraUpdateFactory.zoomTo(defaultZoomLevel));

            }
            mAdapter.notifyDataSetChanged();
            isMapLoaded = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            initializeMapsSettings();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i(MyLog.TAG, "onLocationChanged");
        if (mLastLocation == null) {
            mLastLocation = location;

        }

        mCurrentLocation = location;

        if (Math.round(mCurrentLocation.distanceTo(mLastLocation)) > minChangeDistanceForUpdate || !isMapLoaded) {

            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateLocationUI();
            Toast.makeText(this, "location updated",
                    Toast.LENGTH_SHORT).show();
//        setLocationonMap(location);
//========
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(defaultZoomLevel));

            build_retrofit_and_get_response();

        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    protected void onStart() {
        Log.i(MyLog.TAG, "onStart");
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(MyLog.TAG, "onPause");
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
//            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        Log.i(MyLog.TAG, "onStop");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(MyLog.TAG, "onDestroy");
        super.onDestroy();
    }

    /**
     * Stores activity data in the Bundle.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(MyLog.TAG,  "onSaveInstanceState");
        if (savedInstanceState != null) {
//            savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
            savedInstanceState.putParcelable(KEY_CURRENTLOCATION, mCurrentLocation);
            savedInstanceState.putParcelable(KEY_LASTLOCATION, mLastLocation);
            savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
            Log.i(MyLog.TAG,  "onSaveInstanceState placesList size" +placesList.size());
            savedInstanceState.putParcelableArrayList(KEY_PLACES, placesList);
        }else
            Log.i(MyLog.TAG,  "onSaveInstanceState null");
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onListRowClick(PlaceRow placeRow) {
        Log.i(MyLog.TAG, "onListRowClick ");
        // open new window
        if (placeRow != null) {

            Intent intent = new Intent(this, PlaceActivity.class);
            intent.putExtra("placeRow", placeRow);
            startActivity(intent);


            PlaceCommonInfo commonInfo = placeRow.getCommonInfo();
            Log.i(MyLog.TAG, "id " + commonInfo.getPlaceId());
        }
    }
}
