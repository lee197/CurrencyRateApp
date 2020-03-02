package com.lee.foreignexchangeapp;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/latest")
    Single<CurrencyModel> getCurrencyInfo(@Query("access_key") String apiKey,@Query("symbols") String symbols);
}
