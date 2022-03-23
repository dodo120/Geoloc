package com.uppa.geoloc;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationAPI {

    @GET("reverse")
    Call<GeoResponse> getAdresse(@Query("lon") double longitude, @Query("lat") double latitude);
}
