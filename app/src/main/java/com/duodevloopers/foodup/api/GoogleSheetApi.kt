package com.duodevloopers.foodup.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface GoogleSheetApi {

    @POST("https://script.google.com/macros/s/AKfycbzcJgym5u3Z0WPG7wxuV2tvreb6YUJ3j_0rcX4sQ3IASQ6_IA/exec")
    fun writeToSheet(@QueryMap map: Map<String, String>): Call<Void>

}