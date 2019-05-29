package com.example.geonotepad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.geocam.R;

import java.io.IOException;

public class GeoNotepad extends AppCompatActivity {
    Intent errorIntent = new Intent(this ,Alert.class);
    Intent mainIntent = new Intent(this, MainActivity.class);

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void checkSpace(String message) {
        StatFs stats = new StatFs("/");
        //gets dimensions of camera
        final int  COUNT_STRING_CHARS= message.length();
        final int MAX_LOC_LENGTH_CHARS = 8*2;
        final long AVAILABLE_SPACE = stats.getAvailableBlocksLong();
        final double CHAR_SIZE_BYTES = 1;
        final double MESSAGE_SIZE_BYTES = (COUNT_STRING_CHARS+MAX_LOC_LENGTH_CHARS) * CHAR_SIZE_BYTES;
        //Calculates the size of a photo  in bytes as pixels * 3
        if (MESSAGE_SIZE_BYTES>= AVAILABLE_SPACE) {
            //checks to see if photo will surpass storage ability and launches error if it does
            Bundle stringBundle = new Bundle();
            errorIntent.putExtras(stringBundle);
            stringBundle.putString("There was not enough space on this device", getString(R.string.error));
            startActivity(errorIntent);
        }
    }

    public Location getLocation() {
        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_FINE);
        locationCritera.setCostAllowed(false);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String providerName = locationManager.getBestProvider(locationCritera, true);
        //locationManager.checkPermission();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            //If time, add function to prompt user to change location settings
        }
        return locationManager.getLastKnownLocation(providerName);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void makeMessage() throws IOException {
        DataManipulator dataManipulator = new DataManipulator();
        checkSpace("");
        Location location = getLocation();
        dataManipulator.writeLocationData("", location);
        startActivity(mainIntent);
    }
}


