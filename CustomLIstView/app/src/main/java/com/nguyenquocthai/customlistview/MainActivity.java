package com.nguyenquocthai.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Country>dsQG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dsQG=new ArrayList<Country>();
        Country qg1= new Country("Vietnam",R.drawable.ru3,1000000);
        Country qg2= new Country("United State",R.drawable.ru2,567984534);
        Country qg3= new Country("Russia",R.drawable.ru1,7256);
        dsQG.add(qg1);
        dsQG.add(qg2);
        dsQG.add(qg3);

        ListView lvQG=findViewById(R.id.listviewPro);
        CountryArrayAdapter adapter= new CountryArrayAdapter(dsQG,this);
        lvQG.setAdapter(adapter);
        lvQG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Toast currentToast = null; // Biến để lưu trạng thái của thông báo hiện tại

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //cách 1
                //String mucChon = adapter.getItem(i);
                //cách 2
                String mucChon = dsQG.get(i).getCountryName();
                if (currentToast != null) {
                    currentToast.cancel(); // Tắt thông báo cũ nếu có
                }

                currentToast = Toast.makeText(MainActivity.this, mucChon, Toast.LENGTH_LONG);
                currentToast.show();
            }
        });


    }
}