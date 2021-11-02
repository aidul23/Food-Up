package com.duodevloopers.foodup.myapp;

import android.app.Application;

import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    private static final String TAG = "MyApp";

    public static List<RestaurantItemPojo> mRestaurantItemPojo = new ArrayList<>();

    private static User user;

    private static void getUser() {
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            user = documentSnapshot.toObject(User.class);
                        }
                    }
                });
    }

    public static User getLoggedInUser() {
        return user;
    }

    public static void addItemFromCart(RestaurantItemPojo restaurantItemPojo) {

        List<RestaurantItemPojo> toRemove = new ArrayList<>();

        for (RestaurantItemPojo item : mRestaurantItemPojo) {
            if (item.getmFoodName().equals(restaurantItemPojo.getmFoodName())) {
                toRemove.add(item);
            }
        }

        mRestaurantItemPojo.removeAll(toRemove);
        mRestaurantItemPojo.add(restaurantItemPojo);
    }

    public static void addItem(RestaurantItemPojo restaurantItemPojo) {

        if (mRestaurantItemPojo.contains(restaurantItemPojo)) {
            mRestaurantItemPojo.remove(restaurantItemPojo);
        } else {
            mRestaurantItemPojo.add(restaurantItemPojo);
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            //getUser();
        }
        user = new User();
        user.id = "C171080";
        user.credit = "300.00";
        user.section = "1CM";

    }

    public static void removeItem(RestaurantItemPojo restaurantItemPojo) {
        mRestaurantItemPojo.remove(restaurantItemPojo);
    }

    public static List<RestaurantItemPojo> getmRestaurantItemPojo() {
        return mRestaurantItemPojo;
    }
}
