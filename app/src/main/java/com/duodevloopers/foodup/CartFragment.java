package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duodevloopers.foodup.Adapter.CartAdapter;
import com.duodevloopers.foodup.Adapter.RestaurantAdapter;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.Model.RestaurantPojo;
import com.duodevloopers.foodup.databinding.FragmentCartBinding;
import com.duodevloopers.foodup.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CartFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter adapter;
    private FragmentCartBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: "+MyApp.getmRestaurantItemPojo().size());

        adapter = new CartAdapter(MyApp.getmRestaurantItemPojo());
        binding.cartRecyclerView.setAdapter(adapter);
        binding.cartRecyclerView.setHasFixedSize(true);
        binding.subTotalAmount.setText(String.format("%d Tk",totalCost()));
        binding.totalAmount.setText(String.format("%d Tk",totalCost()+30));
    }

    public int totalCost(){
        int cost = 0;
        for(RestaurantItemPojo item:MyApp.getmRestaurantItemPojo()){
            cost = cost+item.getmFoodPrice();
        }
        return cost;
    }
}