package com.duodevloopers.foodup.myapp

import android.app.Application
import com.duodevloopers.foodup.Model.RestaurantItemPojo
import com.duodevloopers.foodup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (FirebaseAuth.getInstance().currentUser != null) {
            getUser()
        }

    }

    companion object {
        private const val TAG = "MyApp"
        var mRestaurantItemPojo: MutableList<RestaurantItemPojo> = ArrayList()
        var loggedInUser: User? = null

        private fun getUser() {

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        loggedInUser = documentSnapshot.toObject(
                            User::class.java
                        )
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