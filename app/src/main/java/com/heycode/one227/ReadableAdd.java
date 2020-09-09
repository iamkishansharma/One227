package com.heycode.one227;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReadableAdd extends AppCompatActivity {

    // https://console.developers.google.com/project
TextView tv;
    FusedLocationProviderClient myLocationClient;
    boolean trackingFlag;
    LocationCallback myCallback;
    public static final String MYKEY = "my_tracking_location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readable);

        tv = findViewById(R.id.tv);
        myLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(savedInstanceState != null)
            trackingFlag = savedInstanceState.getBoolean(MYKEY);

        /* This is the callback that is triggered when the
          FusedLocationClient updates your location.*/
        myCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(trackingFlag)
                {
                    new GetAddressTask(ReadableAdd.this).
                            execute(locationResult.getLastLocation());
                }
            }
        };

    }

    public void showLocation(View view)
    {
        if(!trackingFlag)
            startTrackingLocation();
        else
            stopTrackingLocation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MYKEY,trackingFlag);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==1)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                startTrackingLocation();
            }
            else
            {
                Toast.makeText(this,"Location Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startTrackingLocation()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            trackingFlag = true;
            Toast.makeText(this,"Location Permission Granted",Toast.LENGTH_SHORT).show();

            // requests periodic location updates
            myLocationClient.requestLocationUpdates(getLocationRequest(),myCallback, null);

        }
    }
    private void stopTrackingLocation()
    {
        if(trackingFlag)
            trackingFlag = false;
    }


    private LocationRequest getLocationRequest()
    {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); // Set the desired interval for active location updates, in milliseconds
        locationRequest.setFastestInterval(5000);// interval of 5000 milliseconds (5 seconds), causes the fused location provider to return location updates that are accurate to within a few feet.
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public  class GetAddressTask extends AsyncTask<Location, Void, String>
    {
        Context ct;
        GetAddressTask(Context ct)
        {
            this.ct =ct;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(ct, Locale.getDefault());

            // Get the passed in location
            Location location = locations[0];
            List<Address> addressesList = null;
            String resultMessage = "";

            try {
                addressesList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                if(addressesList.size() >0)
                {
                    Address fetchAddress = addressesList.get(0);
                    ArrayList<String> addressParts = new ArrayList<>();

                    for (int i = 0; i <= fetchAddress.getMaxAddressLineIndex(); i++) {
                        addressParts.add(fetchAddress.getAddressLine(i));
                    }
                    resultMessage = String.join("\n",addressParts);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals(""))
                tv.setText(s);
        }
    }


    public void openMap(View view)
    {
        startActivity(new Intent(this, GoogleMapsActivity.class));
    }
}

