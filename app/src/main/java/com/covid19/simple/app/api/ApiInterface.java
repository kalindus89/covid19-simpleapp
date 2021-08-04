package com.covid19.simple.app.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("countries")
    Call<List<ModelClass>> getCountryData();


}
