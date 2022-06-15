package com.redeyesncode.rebootmemvvm.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String URL = "YOUR_API_END_POINT_HERE";

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build();


    //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Gson gson = new GsonBuilder().setLenient().create();

    private static final Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL);
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        return retrofit.create(serviceClass);
    }



    public static Retrofit getRetrofit() {
        return builder.client(httpClient).build();
    }
}
