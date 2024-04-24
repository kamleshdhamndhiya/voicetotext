package com.test.speechtotext.utility;


import com.google.gson.JsonObject;
import com.test.speechtotext.requestModel.CompletionRequest;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoadInterface {

    @POST("completions")
    Call<ResponseBody> completions(@Body CompletionRequest completionRequest);

   }


