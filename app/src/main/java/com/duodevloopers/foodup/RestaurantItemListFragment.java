package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duodevloopers.foodup.Adapter.FragmentAdapter;
import com.duodevloopers.foodup.Adapter.RestaurantItemListAdapter;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.databinding.FragmentRestaurantItemListBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RestaurantItemListFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantItemListAdapter adapter;
    private FragmentRestaurantItemListBinding binding;

    public RestaurantItemListFragment() {
        // Required empty public constructor
    }

    public static RestaurantItemListFragment newInstance(String param1, String param2) {
        RestaurantItemListFragment fragment = new RestaurantItemListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        args.getString(RestaurantFragment.ARG_OBJECT);
        args.getString(FragmentAdapter.RESTAURANT_NAME_KEY);
        Log.d(TAG, "onCreate: "+args.getString(FragmentAdapter.RESTAURANT_NAME_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantItemListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ArrayList<RestaurantItemPojo> restaurantItemPojoArrayList = new ArrayList<>();
        restaurantItemPojoArrayList.add(new RestaurantItemPojo(R.drawable.food_icon, "White rice & Chicken ", 50));
        restaurantItemPojoArrayList.add(new RestaurantItemPojo(R.drawable.food_icon, "Khichuri & Chicken", 60));
        restaurantItemPojoArrayList.add(new RestaurantItemPojo(R.drawable.food_icon, "Chicken Biriyani", 80));
        restaurantItemPojoArrayList.add(new RestaurantItemPojo(R.drawable.food_icon, "Mineral Water", 15));



        binding.restaurantItemRecyclerView.setHasFixedSize(true);
        adapter = new RestaurantItemListAdapter(restaurantItemPojoArrayList);
        binding.restaurantItemRecyclerView.setAdapter(adapter);

        setOnCliskListener();
    }

    private void setOnCliskListener() {
        adapter.setListener(new RestaurantItemListAdapter.RecyclerViewListItemClickListener() {
            @Override
            public void onClick(RestaurantItemPojo restaurantItemPojo) {
                Log.d(TAG, "onClick: "+restaurantItemPojo.getmFoodName());

                    MyApp.addItem(restaurantItemPojo);

            }

        });
    }
}