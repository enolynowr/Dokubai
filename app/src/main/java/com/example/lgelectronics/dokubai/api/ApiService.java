package com.example.lgelectronics.dokubai.api;

import com.example.lgelectronics.dokubai.model.PhotozouResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by LG Electronics on 2017/08/20.
 */

public interface ApiService {

    @Headers({"Accept: application/json"})
    @GET("rest/search_public.json")
    Call<PhotozouResponse> getPhotozou(@Query("keyword") String keyword,@Query("limit") String limit );
}
