package vn.nguyenquocthai.searchlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Response;

import java.util.ArrayList;
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocations(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchLocations(newText);
                return false;
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
                    List<String> locationNames = new ArrayList<>();
                    for (LocationResult location : response.body()) {
                        locationNames.add(location.getDisplay_name());
                    }

                    // Cập nhật dữ liệu của adapter trên UI thread
                    runOnUiThread(() -> {
                        adapter.clear();
                        adapter.addAll(locationNames);
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