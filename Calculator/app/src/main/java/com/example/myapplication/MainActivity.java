package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public float Input(TextView in){
        float num = 0f;
        try {
            num = Float.parseFloat(in.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("info", "Wrong input");
        }
        return num;
    }

    public void Action(View v) {
        float l = Input(findViewById(R.id.num1));
        float r = Input(findViewById(R.id.num2));
        Button button = (Button)v;
        TextView mTextView = findViewById(R.id.result);

        String check = button.getText().toString();
        double result = 0.0;
        if (check.equals("+")) {
            result = l + r;
        } else if (check.equals("-")) {
            result = l - r;
        } else if (check.equals("*")) {
            result = l * r;
        } else if (check.equals("/")) {
            if (r != 0) {
                result = l / r;
            } else {
                Log.d("info", "Không thể chia cho 0");
                return;
            }
        } else {
            Log.d("info", "Chưa chọn nút");
            return;
        }

        String formattedResult = String.format("%.1f", result);
        mTextView.setText(l + " " + check + " " + r + " = " + formattedResult);
    }

}