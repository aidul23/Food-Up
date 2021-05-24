package com.duodevloopers.foodup.Adapter;

import android.os.Bundle;

import com.duodevloopers.foodup.RestaurantFragment;
import com.duodevloopers.foodup.RestaurantItemListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    String restaurantName;
    public static final String RESTAURANT_NAME_KEY = "restaurant_name_key";

    public FragmentAdapter(@NonNull Fragment fragment,String restaurantName) {
        super(fragment);
        this.restaurantName = restaurantName;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new RestaurantItemListFragment();
        Bundle args = new Bundle();
        args.putString(RestaurantFragment.ARG_OBJECT, RestaurantFragment.TAB_TITLES[position]);
        args.putString(RESTAURANT_NAME_KEY, restaurantName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
