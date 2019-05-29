package com.example.geonotepad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.geocam.R;

import java.io.IOException;

public class GeoOpener extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_roll);
    }

    public void getString(View view){
        openGoogleMaps("");
    }
    public void openGoogleMaps(String enteredName){
        DataManipulator dataManipulator = new DataManipulator();
        try {
            Coordinate locationData = dataManipulator.readLocationData(enteredName);
            if (locationData == null) {
                Intent errorIntent = new Intent(this, Alert.class);
                Bundle stringBundle = new Bundle();
                stringBundle.putString("The selected location did not have related location\n data", getString(R.string.error));
                startActivity(errorIntent);
            }
            Uri mapsUri = Uri.parse("google.streetview:cbll="+locationData.Latitude+", "+locationData.Longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, mapsUri);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
