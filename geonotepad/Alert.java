package com.example.geonotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.geocam.R;

public class Alert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = (TextView) findViewById(R.id.textView2);
        setContentView(R.layout.activity_photo_not_found_alert);
        text.setText("");

    }

    public void revertActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

