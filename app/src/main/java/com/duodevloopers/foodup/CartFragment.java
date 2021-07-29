package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private MainActivityViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        model.getSubTotal().setValue(totalCost());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
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

        model.getSubTotal().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.subTotalAmount.setText(String.format("%d Tk",integer));
                binding.totalAmount.setText(String.format("%d Tk",integer - 30));
            }
        });

        adapter.setListener(new CartAdapter.CartItemClickListener() {
            @Override
            public void onClick() {

            }

            @Override
            public void onIncreaseItem(int price) {
                model.increaseSubtotal(price);
            }

            @Override
            public void onDecreaseItem(int price) {
                model.decreaseSubTotal(price);
            }
        });


//        binding.subTotalAmount.setText(String.format("%d Tk",totalCost()));
//        binding.totalAmount.setText(String.format("%d Tk",totalCost() - 30));

    }

    public int totalCost(){
        int cost = 0;
        for(RestaurantItemPojo item:MyApp.getmRestaurantItemPojo()){
            cost = cost + item.getmFoodPrice();
        }
        return cost;
    }
}