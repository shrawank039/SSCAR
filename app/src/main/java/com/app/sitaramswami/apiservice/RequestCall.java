package com.app.sitaramswami.apiservice;

import android.util.Log;

import com.app.sitaramswami.LoginData;
import com.app.sitaramswami.interfaces.ApiService;
import com.app.sitaramswami.interfaces.RetrofitListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

/**
 * Created by kartik on 04-May-18.
 */

public class RequestCall {
    private ApiService service;

    public RequestCall() {
        Retrofit retrofit = ServiceGenerator.getService();
        service = retrofit.create(ApiService.class);
    }

    public void post(String url, JsonElement arguments, final RetrofitListener listener, final int fromCalling) {
        Call<JsonElement> call = service.postCall(url, arguments);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               listener.onResponse(response.body(),fromCalling);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                listener.onError(t.getMessage(),fromCalling);
            }
        });


    }
    public void get(String url,  final RetrofitListener listener, final int fromCalling) {
        Call<JsonElement> call = service.getCall(url);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                listener.onResponse(response.body(),fromCalling);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                listener.onError(t.getMessage(),fromCalling);
            }
        });


    }
}
