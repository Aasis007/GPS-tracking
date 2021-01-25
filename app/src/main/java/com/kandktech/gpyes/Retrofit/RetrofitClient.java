package com.kandktech.gpyes.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://darlagyomylord.com/gps_tracker/public/api/";

    public static Retrofit getRetrofit() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .build();

        return retrofit;
    }


    public static ApiInterface getApiInterface() {
        ApiInterface apiInterface = getRetrofit().create(ApiInterface.class);
        return apiInterface;
    }



}
