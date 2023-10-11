package com.example.loginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String Input(TextView in){
        String text = "";
        try {
            text = (in.getText().toString());
        } catch (Exception e) {
            Log.d("info", "Nhập sai");
        }
        return text;
    }
    public void XuLyLogin(View v){
        String name=Input(findViewById(R.id.editUserName));
        String pass=Input(findViewById(R.id.editUserPassword));
        if(name.equals("trong")&& pass.equals("123"))
            Toast.makeText(this,"Login success",Toast.LENGTH_LONG).show();
        else{
            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();

        }
    }
}