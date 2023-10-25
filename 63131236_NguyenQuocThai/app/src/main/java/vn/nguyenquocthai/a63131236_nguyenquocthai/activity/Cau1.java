package vn.nguyenquocthai.a63131236_nguyenquocthai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;

public class Cau1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau1);
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
        float l = Input(findViewById(R.id.editText_NumA));
        float r = Input(findViewById(R.id.editText_NumB));
        Button button = (Button)v;
        TextView mTextView = findViewById(R.id.editText_KetQua);

        String check ="+";
        double result = 0.0;

        result = l + r;



        String formattedResult = String.format("%.1f", result);
        mTextView.setText(formattedResult);
    }
}