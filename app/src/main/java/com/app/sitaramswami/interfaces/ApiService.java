package com.app.sitaramswami.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by kartik on 04-May-18.
 */

public interface ApiService {


    //  @Headers({"Content-Type:application/json"})
    @POST
    Call<JsonElement> postCall(@Url String url, @Body JsonElement args);

    @GET
    Call<JsonElement> getCall(@Url String url);
}
