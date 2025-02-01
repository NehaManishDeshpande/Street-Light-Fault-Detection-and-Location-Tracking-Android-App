package com.example.streetlightfaultdetectionandlocationtracking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThingSpeakApi {
    @GET("channels/2561588/feeds.json")
    Call<ThingSpeakResponse> getFeeds(@Query("api_key") String apiKey, @Query("results") int results);
}
