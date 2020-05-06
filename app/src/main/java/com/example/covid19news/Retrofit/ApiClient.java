package com.example.covid19news.Retrofit;

import com.example.covid19news.ApiInterface.ApiInterface;
import com.example.covid19news.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static ApiInterface getApiInterface(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpParam.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

    private static OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? BODY :NONE);

        return interceptor;
    }
}
