package com.duodevloopers.foodup.repository

import android.util.Log
import com.duodevloopers.foodup.Model.User
import com.duodevloopers.foodup.api.GoogleSheetApi
import com.duodevloopers.foodup.utility.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    fun writeToSheet(user: User, courseCode : String) {

        val googleSheetApi =
            Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleSheetApi::class.java)

        val userMap = HashMap<String, String>()


        userMap["name"] = user.name
        userMap["id"] = user.id
        userMap["section"] = user.section
        userMap["date"] = Utility.formatMillisecondsIntoDate(System.currentTimeMillis())
        userMap["course code"] = courseCode

        GlobalScope.launch(Dispatchers.IO) {
            val response = googleSheetApi.writeToSheet(userMap)
            if (response.isSuccessful) {
                Log.d("APICALL", "writeToSheet: successful")
            } else {
                Log.d("APICALL", "writeToSheet: unsuccessful")
            }
        }
    }

}