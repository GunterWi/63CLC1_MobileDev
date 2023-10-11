package com.example.nguyenquocthai.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWidget();
        lst.add("Java");
        lst.add("JavaScript");
        lst.add("C++");
        lst.add("Swift");
        lst.add("Ruby");
        lst.add("PHP");
        lst.add("Kotlin");
        lst.add("Go");
        lst.add("Rust");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lst);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Toast currentToast = null; // Biến để lưu trạng thái của thông báo hiện tại

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //cách 1
                String mucChon = adapter.getItem(i);
                //cách 2
                //String mucChon = lst.get(i).toString();
                if (currentToast != null) {
                    currentToast.cancel(); // Tắt thông báo cũ nếu có
                }

                currentToast = Toast.makeText(getApplicationContext(), mucChon, Toast.LENGTH_LONG);
                currentToast.show();
            }
        });
    }
    public void getWidget(){
        listview=findViewById(R.id.listView);
    }
    ListView listview;
    List<String> lst=new ArrayList<>();
}