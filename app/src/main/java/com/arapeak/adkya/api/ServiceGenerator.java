package com.arapeak.adkya.api;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = "https://adkya.com/api/v1/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService( Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S createService( Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }



}
