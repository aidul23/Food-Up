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

    public static void addItem(RestaurantItemPojo restaurantItemPojo){
        mRestaurantItemPojo.add(restaurantItemPojo);
    }

    public static List<RestaurantItemPojo> getmRestaurantItemPojo() {
        return mRestaurantItemPojo;
    }
}
