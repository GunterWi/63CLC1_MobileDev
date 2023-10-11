package com.example.nguyenquocthai.autocompleteexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView auTV;
    List<String> COUNTRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auTV = findViewById(R.id.autocompletetv);

        // Initialize the list and add items
        COUNTRIES = new ArrayList<>();
        COUNTRIES.add("Australia");
        COUNTRIES.add("England");
        COUNTRIES.add("Viet Nam");
        COUNTRIES.add("Albania");
        COUNTRIES.add("USA");
        COUNTRIES.add("Algeria");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        auTV.setAdapter(adapter);
    }
}
