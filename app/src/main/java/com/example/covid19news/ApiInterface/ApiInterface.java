package com.example.covid19news.ApiInterface;

import com.example.covid19news.Model.CountryModel;
import com.example.covid19news.Model.GlobalModel;
import com.example.covid19news.Retrofit.HttpParam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(HttpParam.API_GET_GLOBAL_COUNT)
    Call<GlobalModel> getGlobalDetails();

    @GET(HttpParam.API_GET_ALL_COUNTRY_COUNT)
    Call<List<CountryModel>> getCountryDetails();
}
