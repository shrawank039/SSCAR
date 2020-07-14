package com.app.sitaramswami.interfaces;

import com.google.gson.JsonElement;

/**
 * Created by kartik on 04-May-18.
 */

public interface RetrofitListener {
 void onResponse(JsonElement response, int fromCalling);

   void onError(String message, int fromCalling);
}
