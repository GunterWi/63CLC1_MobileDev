package com.example.examplechangefont;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.app_bar_main);
        setSupportActionBar(toolbar);
    }
    public void XuLyNhanOK(View v){
        Toast.makeText(this ,"vũngu",Toast.LENGTH_LONG).show();
    }
}