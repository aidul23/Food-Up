package com.duodevloopers.foodup.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface GoogleSheetApi {

    @POST("https://script.google.com/macros/s/AKfycbzq0_33lKE0tFAaxOWa5jcvyRL6umg-Q8fzBqmGmm18wWBvcCG_4zObNi1DXLNaT5M/exec")
    fun writeToSheet(@QueryMap map: Map<String, String>): Call<Void>

}