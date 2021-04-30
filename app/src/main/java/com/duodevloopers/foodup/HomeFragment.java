package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.models.SlideModel;
import com.duodevloopers.foodup.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        binding.textViewGreeting.setText("Welcome, Aidul");
        binding.textViewRestaurantNumber.setText("What's for dinner? There are 20 restaurant in your area");

        ArrayList<RestaurantPojo> restaurantPojoArrayList = new ArrayList<>();
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food,"Sultan's Dine","Biriyani Restaurant","5.0"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food,"Bir Chottrola","Indo Bangla","4.5"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food,"Barcode","Italian","5.0"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food,"Mughal","Indian","5.0"));

        binding.homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RestaurantAdapter(restaurantPojoArrayList);

        binding.homeRecyclerView.setLayoutManager(layoutManager);
        binding.homeRecyclerView.setAdapter(adapter);
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://image.shutterstock.com/image-photo/various-asian-meals-on-rustic-260nw-1125066479.jpg"));
        slideModels.add(new SlideModel("https://image.shutterstock.com/image-photo/various-asian-meals-on-rustic-260nw-1075946798.jpg"));
        slideModels.add(new SlideModel("https://image.shutterstock.com/image-photo/italian-food-background-pasta-meat-260nw-678135781.jpg"));
        binding.homeImageSlider.setImageList(slideModels,true);
    }
}