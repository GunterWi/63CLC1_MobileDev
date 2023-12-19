package vn.nguyenquocthai.searchlocation;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;


public interface OpenStreetMapApi {
    @GET("search")
    Call<List<LocationResult>> searchLocations(
            @Query("format") String format,
            @Query("q") String query
    );
}