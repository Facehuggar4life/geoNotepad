package com.example.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openOpener(View view) {
        Intent intent = new Intent(this, GeoOpener.class);
        startActivity(intent);
    }
    public void openNotepad(View view){
        Intent intent = new Intent(this, GeoNotepad.class);
        startActivity(intent);
    }
}
