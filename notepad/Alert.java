package com.example.notepad;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Alert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        boolean isLocationAlert = bundle.getBoolean("isLocationAlert");
        System.out.println(isLocationAlert);
        if(isLocationAlert){
            setContentView(R.layout.location_alert);
        }
        else{
            TextView text = (TextView) findViewById(R.id.error_textbox);
            setContentView(R.layout.activity_alert);
            text.setText(savedInstanceState.getString("textContent"));
        }
    }

    public void revertActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchLocationpreferences(View view){
        Intent setttingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        startActivity(setttingsIntent);
    }
}


