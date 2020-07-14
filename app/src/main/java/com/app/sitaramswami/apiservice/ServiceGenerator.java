package com.app.sitaramswami.apiservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by beyond on 24-Feb-17.
 */

public class ServiceGenerator {
    public static Retrofit getService() {

       /* OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type","application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();*/

        Retrofit retrofit = new Retrofit.Builder().

                baseUrl("http://app.sscarbike.com/admin/api/").
              //  client(httpClient).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return retrofit;
    }
}
