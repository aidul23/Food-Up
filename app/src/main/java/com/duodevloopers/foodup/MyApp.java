package com.duodevloopers.foodup;

import android.app.Application;

import com.duodevloopers.foodup.Model.RestaurantItemPojo;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    public static List<RestaurantItemPojo> mRestaurantItemPojo = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void addItem(RestaurantItemPojo restaurantItemPojo) {

        if (mRestaurantItemPojo.contains(restaurantItemPojo)) {
            mRestaurantItemPojo.remove(restaurantItemPojo);
        } else {
            mRestaurantItemPojo.add(restaurantItemPojo);
        }

    }

    public static void addItemFromCart(RestaurantItemPojo restaurantItemPojo) {

        for (RestaurantItemPojo item : mRestaurantItemPojo) {
            if (item.getmFoodName().equals(restaurantItemPojo.getmFoodName())) {
                mRestaurantItemPojo.remove(item);
                mRestaurantItemPojo.add(restaurantItemPojo);
            }
        }

    }

    public static void removeItem(RestaurantItemPojo restaurantItemPojo) {
        mRestaurantItemPojo.remove(restaurantItemPojo);
    }

    public static List<RestaurantItemPojo> getmRestaurantItemPojo() {
        return mRestaurantItemPojo;
    }
}
