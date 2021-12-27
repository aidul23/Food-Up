package com.duodevloopers.foodup.myapp

import android.app.Application
import android.util.Log
import com.duodevloopers.foodup.Model.RestaurantItemPojo
import com.duodevloopers.foodup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (FirebaseAuth.getInstance().currentUser != null) {
            getUser()
            getPrintShops()
        }

    }


    companion object {
        private const val TAG = "MyApp"
        var mRestaurantItemPojo: MutableList<RestaurantItemPojo> = ArrayList()
        var loggedInUser: User? = null
        var shopList: MutableList<String> = ArrayList()

        private fun getUser() {

            val ref = FirebaseFirestore.getInstance().collection("student")
                .document(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)

            ref.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {

                    loggedInUser = snapshot.toObject(
                        User::class.java
                    )

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
        }

         fun getPrintShops() {
            FirebaseFirestore.getInstance()
                .collection("shops")
                .get()
                .addOnSuccessListener {
                    for (shop in it.documents) {

                        if (shop["type"] != "food") shopList.add(shop["name"].toString())

                    }
                }
        }

        @JvmStatic
        fun addItemFromCart(restaurantItemPojo: RestaurantItemPojo) {
            val toRemove: MutableList<RestaurantItemPojo> = ArrayList()
            for (item in mRestaurantItemPojo) {
                if (item.getmFoodName() == restaurantItemPojo.getmFoodName()) {
                    toRemove.add(item)
                }
            }
            mRestaurantItemPojo.removeAll(toRemove)
            mRestaurantItemPojo.add(restaurantItemPojo)
        }

        @JvmStatic
        fun addItem(restaurantItemPojo: RestaurantItemPojo) {
            if (mRestaurantItemPojo.contains(restaurantItemPojo)) {
                mRestaurantItemPojo.remove(restaurantItemPojo)
            } else {
                mRestaurantItemPojo.add(restaurantItemPojo)
            }
        }

        fun removeItem(restaurantItemPojo: RestaurantItemPojo) {
            mRestaurantItemPojo.remove(restaurantItemPojo)
        }

        @JvmStatic
        fun getmRestaurantItemPojo(): List<RestaurantItemPojo> {
            return mRestaurantItemPojo
        }
    }

}