package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duodevloopers.foodup.Adapter.CartAdapter;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.Model.SummaryItemPojo;
import com.duodevloopers.foodup.databinding.FragmentCartBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CartFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter adapter;
    private FragmentCartBinding binding;
    private MainActivityViewModel model;
    Bundle bundle = new Bundle();

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

        if(MyApp.getmRestaurantItemPojo().size() == 0) {
            binding.subTotal.setVisibility(View.GONE);
            binding.subTotalAmount.setVisibility(View.GONE);
            binding.discount.setVisibility(View.GONE);
            binding.discountAmount.setVisibility(View.GONE);
            binding.total.setVisibility(View.GONE);
            binding.totalAmount.setVisibility(View.GONE);
            binding.proceedButton.setVisibility(View.GONE);

            binding.imageViewCart.setVisibility(View.VISIBLE);
            binding.textViewCart.setVisibility(View.VISIBLE);
        }

        adapter = new CartAdapter(MyApp.getmRestaurantItemPojo());
        binding.cartRecyclerView.setAdapter(adapter);
        binding.cartRecyclerView.setHasFixedSize(true);

        model.getSubTotal().observe(getViewLifecycleOwner(), new Observer<Integer>() {

            @Override
            public void onChanged(Integer integer) {
                binding.subTotalAmount.setText(String.format("%d Tk",integer));


                bundle.putString("subtotal", String.valueOf(integer));


                if(integer >= 50){
                    binding.discountAmount.setText("-30 Tk");
                    binding.totalAmount.setText(String.format("%d Tk",integer - 30));
                } else {
                    binding.totalAmount.setText(String.format("%d Tk",integer));
                }

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


        binding.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

//                navController.navigate(action);

//                Map<String,Object> foodItem = new HashMap<>();
//                foodItem.put("selectedFood",MyApp.getmRestaurantItemPojo());
//                Log.d(TAG, "onClick: "+foodItem);

//                RestaurantItemPojo restaurantItemPojo = new RestaurantItemPojo(0,"Chicken Biriyani",80);
                SummaryItemPojo summaryItemPojo = new SummaryItemPojo(MyApp.getmRestaurantItemPojo());

                CartFragmentDirections.ActionCartFragmentToSummaryFragment action = CartFragmentDirections.actionCartFragmentToSummaryFragment(summaryItemPojo, bundle.getString("subtotal"));
//                NavDirections action = CartFragmentDirections.actionCartFragmentToSummaryFragment();
                navController.navigate(action);

            }
        });

    }

    public int totalCost(){
        int cost = 0;
        for(RestaurantItemPojo item:MyApp.getmRestaurantItemPojo()){
            cost = cost + item.getmFoodPrice();
        }
        return cost;
    }

//    private void moveToNewActivity () {
//
//        Intent intent = new Intent(getActivity(), MapActivity.class);
//        startActivity(intent);
//        ((Activity) getActivity()).overridePendingTransition(0, 0);
//
//    }
}