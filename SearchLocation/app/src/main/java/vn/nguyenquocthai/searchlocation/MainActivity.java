package vn.nguyenquocthai.searchlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private OpenStreetMapApi openStreetMapApi;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private List<String> cities = new ArrayList<>(Arrays.asList("Hà Nội", "Thành phố Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", "Nha Trang", "Hải Dương", "..."));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openStreetMapApi = retrofit.create(OpenStreetMapApi.class);


        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        listView.setVisibility(View.GONE);
        searchView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                listView.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocations(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Lọc danh sách dựa trên newText
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Xử lý sự kiện chọn một gợi ý
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = adapter.getItem(position);
            if (selectedCity != null) {
                searchLocations(selectedCity);
                //Toast.makeText(this,selectedCity,Toast.LENGTH_LONG).show();
            }
        });
    }
//https://nominatim.openstreetmap.org/search?format=json&q=nha%20trang
private void searchLocations(String query) {
    Call<List<LocationResult>> call = openStreetMapApi.searchLocations("json", query);
    call.enqueue(new Callback<List<LocationResult>>() {

        @Override
        public void onResponse(Call<List<LocationResult>> call, retrofit2.Response<List<LocationResult>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<String> locationDetails = new ArrayList<>();
                for (LocationResult location : response.body()) {
                    String detail = location.getDisplay_name() + "\nLat: " + location.getLat() + ", Lon: " + location.getLon();
                    locationDetails.add(detail);
                    Toast.makeText(MainActivity.this,detail,Toast.LENGTH_LONG).show();
                }

                // Cập nhật dữ liệu của adapter trên UI thread
                runOnUiThread(() -> {
                    adapter.clear();
                    adapter.addAll(locationDetails);
                    adapter.notifyDataSetChanged();
                });
            }
        }

        @Override
        public void onFailure(Call<List<LocationResult>> call, Throwable t) {
            // Xử lý khi có lỗi xảy ra
        }
    });
}

}