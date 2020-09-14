package com.heycode.one227;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class GoogleTopTenPlaces extends FragmentActivity {

    private GoogleMap gMap;
    SupportMapFragment mapFragment;

    FusedLocationProviderClient fusedLocationProviderClient;
    boolean trackingFlag;
    LocationCallback locationCallback;
    double currentLatitude, currentLongitude;

    String myKey ="AIzaSyA8vrtB2ixsXo1kSPNiMy36KDXsGOvr5iA";
    AutocompleteSupportFragment supportFragment;
    PlacesClient placesClient;
    ListView placeList;
    public static final int MAX=10;
    String [] placename, placeAddress;
    LatLng[] placeLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_top_ten_places);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.autocomplete_fragment);

        placeList = findViewById(R.id.lv);


        fusedLocationProviderClient = LocationServices.
                getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (trackingFlag) {
                    new LocationTask().execute(locationResult.
                            getLastLocation());
                } }
        };
        checkLocationPermission();
        if (!Places.isInitialized())
            Places.initialize(this,myKey);

        placesClient = Places.createClient(this);

        supportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                Place.Field.NAME));
        supportFragment.setOnPlaceSelectedListener(new
                                                           PlaceSelectionListener() {
                                                               @Override
                                                               public void onPlaceSelected(@NonNull Place place) {
                                                                   if (place != null)
                                                                   {
                                                                       Toast.makeText(GoogleTopTenPlaces.this, place.getName(),
                                                                               Toast.LENGTH_SHORT).show();
                                                                       gMap.clear();
                                                                       LatLng latLng = place.getLatLng();
                                                                       gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                                                       MarkerOptions markerPlace = new MarkerOptions();
                                                                       markerPlace.position(latLng);
                                                                       markerPlace.title("Place is:- "+ place.getName());

                                                                       gMap.addMarker(markerPlace);

                                                                   }
                                                               }

                                                               @Override
                                                               public void onError(@NonNull Status status)
                                                               {

                                                               }
                                                           });

    }

    private void checkLocationPermission()
    {
        if (!trackingFlag)
            startTrackingLocation();
        else
            stopTrackingLocation();
    }

    private void startTrackingLocation()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            Toast.makeText(this, "Location Permission Already Granted",
                    Toast.LENGTH_SHORT).show();
            trackingFlag = true;

            fusedLocationProviderClient.requestLocationUpdates(getLocationRequest(),
                    locationCallback,null);

        }
    }

    private LocationRequest getLocationRequest()
    {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return locationRequest;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==1)
        {
            if (grantResults.length >0 && grantResults [0] == PackageManager.
                    PERMISSION_GRANTED)
            {
                startTrackingLocation();
            }
            else
                Toast.makeText(this, "Permission denied by user",
                        Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTrackingLocation()
    {
        if (trackingFlag)
            trackingFlag = false;
    }

    public void showPlaces(View view)
    {
        List<Place.Field> placeField = Arrays.asList(Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeField)
                .build();

        if (trackingFlag)
        {
            @SuppressLint("MissingPermission")
            Task<FindCurrentPlaceResponse> responseTask = placesClient.findCurrentPlace
                    (request);

            responseTask.addOnCompleteListener(new OnCompleteListener
                    <FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {

                    if (task.isSuccessful())
                    {
                        FindCurrentPlaceResponse placeResponse = task.getResult();
                        List<PlaceLikelihood> likelihoodList = placeResponse.
                                getPlaceLikelihoods();
                        int count;
                        if (likelihoodList.size() < MAX)
                            count = likelihoodList.size();

                        else
                            count= MAX;

                        placename = new String[count];
                        placeAddress = new String[count];
                        placeLatLng = new LatLng[count];
                        int i=0;
                        for (PlaceLikelihood placeLikelihood : likelihoodList)
                        {
                            placename[i]= placeLikelihood.getPlace().getName();
                            placeAddress[i]= placeLikelihood.getPlace().getAddress();
                            placeLatLng[i]= placeLikelihood.getPlace().getLatLng();
                            i++;
                            if (i == count)
                                break;
                        }
                        fillPlaceDetails();
                    }
                    else
                    {
                        Toast.makeText(GoogleTopTenPlaces.this, task.getException()
                                .getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void fillPlaceDetails()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,placename);


        placeList.setAdapter(adapter);
    }

    class LocationTask extends AsyncTask<Location, Void, String> {

        @Override
        protected String doInBackground(Location... locations)
        {
            Location myLocation = locations[0];
            String msg= "";
            if (myLocation != null) {
                currentLatitude = myLocation.getLatitude();
                currentLongitude = myLocation.getLongitude();
                msg = "done";
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.equals("done"))
            {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        gMap = googleMap;
                        gMap.clear();
                        LatLng mylatlng = new LatLng(currentLatitude,currentLongitude);
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlng));
                        gMap.setMyLocationEnabled(true);
                        MarkerOptions curentMarker = new MarkerOptions();
                        curentMarker.position(mylatlng);
                        curentMarker.title("You are Here");
                        gMap.addMarker(curentMarker);
                    }
                });
            }
        }
    }
}