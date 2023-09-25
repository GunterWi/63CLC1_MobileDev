package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public int Input(TextView in){
        int num = 0;
        try {
            num = Integer.parseInt(in.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("info", "Nhập sai");
        }
        return num;
    }

    public void CalculateClick(View v){
        int h = Input(findViewById(R.id.HeightTb));
        int w = Input(findViewById(R.id.WeightTb));
        double result = (w * 1.0) / Math.pow(h / 100.0, 2);
        Log.d("info",Double.toString(Math.pow(h / 100.0, 2)));
        TextView mTextView = findViewById(R.id.Point);
        mTextView.setText(String.valueOf(String.format("%.1f", result))); // Chuyển đổi result thành chuỗi và đặt vào TextView
        TextView StatusView = findViewById(R.id.Status);
        if(result<18.5){
            StatusView.setText("Underweight");
        }else if(result<25){
            StatusView.setText("Normal");
        }else if(result<30){
            StatusView.setText("Overweight");
        }else if(result<35){
            StatusView.setText("Obese Class I");
        }else if(result<40){
            StatusView.setText("Obese Class II");
        }else{
            StatusView.setText("Obese Class III");
        }
    }
}