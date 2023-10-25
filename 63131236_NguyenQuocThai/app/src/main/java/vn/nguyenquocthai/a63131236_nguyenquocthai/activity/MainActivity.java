package vn.nguyenquocthai.a63131236_nguyenquocthai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initiation();
        cau1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cau1.class));
            }
        });
        cau2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cau2.class));
            }
        });
        cau3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cau3.class));
            }
        });
        cau4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cau4.class));
            }
        });

    }
    private void Initiation() {
        cau1 = findViewById(R.id.btn1);
        cau2 = findViewById(R.id.btn2);
        cau3 = findViewById(R.id.btn3);
        cau4 = findViewById(R.id.btn4);

    }
    private TextView cau1,cau2,cau3,cau4;
}