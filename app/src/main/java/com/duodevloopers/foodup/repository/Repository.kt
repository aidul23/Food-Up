package com.duodevloopers.foodup.repository

import com.duodevloopers.foodup.Model.User
import com.duodevloopers.foodup.api.GoogleSheetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    fun writeToSheet(user: User) {

        val googleSheetApi =
            Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleSheetApi::class.java)

        val userMap = HashMap<String, String>()

        userMap["id"] = user.id
        userMap["section"] = user.section
        userMap["department"] = user.department
        userMap["name"] = user.name
        userMap["number"] = user.number
        userMap["type"] = user.type

        GlobalScope.launch(Dispatchers.IO) {
            googleSheetApi.writeToSheet(userMap).await()
        }
    }

}