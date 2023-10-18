package com.nguyenquocthai.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Country>dsQG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dsQG=new ArrayList<Country>();
        Country qg1= new Country("Vietnam","ru1",1000000);
        Country qg2= new Country("United State","ru2",567984534);
        Country qg3= new Country("Russia","ru3",7256);
        dsQG.add(qg1);
        dsQG.add(qg2);
        dsQG.add(qg3);

        ListView lvQG=findViewById(R.id.listviewPro);
        CountryArrayAdapter adapter= new CountryArrayAdapter(dsQG,this);
        lvQG.setAdapter(adapter);


    }
}