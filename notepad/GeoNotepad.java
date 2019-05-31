package com.example.notepad;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class GeoNotepad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_notepad);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
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
            Intent errorIntent = new Intent(this ,Alert.class);
            //checks to see if photo will surpass storage ability and launches error if it does
            Bundle stringBundle = new Bundle();
            //errorIntent.putExtras(stringBundle);
            stringBundle.putBoolean("isLocationAlert", false);
            stringBundle.putString("textContent","There was not enough space on this device");
            errorIntent.putExtras(stringBundle);
            startActivity(errorIntent);
        }
    }

    public Location getLocation() {
        Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationCriteria.setCostAllowed(false);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String providerName = locationManager.getBestProvider(locationCriteria, true);
        //locationManager.checkPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            //If time, add function to prompt user to change location settings
            return null;
        }
        return locationManager.getLastKnownLocation(providerName);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void makeMessage(View view) throws IOException {
        Intent mainIntent = new Intent(this, MainActivity.class);
        EditText textField = (EditText) findViewById(R.id.notepad_text_field);
        String inputText = textField.getText().toString();
        DataManipulator dataManipulator = new DataManipulator();
        checkSpace(inputText);
        Location location = getLocation();
        if(location==null){
            System.out.println("Reached 1");
            startLocationError();
        }
        else {
            //writes location name and data to file
            dataManipulator.writeLocationData(inputText, location);
            //returns to main menu
            startActivity(mainIntent);
        }
    }

    public void startLocationError(){
        Intent errorIntent = new Intent(this, Alert.class);
        Bundle stringBundle = new Bundle();
        stringBundle.putBoolean("isLocationAlert", true);
        errorIntent.putExtras(stringBundle);
        startActivity(errorIntent);
        System.out.println("Really?!?");
    }
}


