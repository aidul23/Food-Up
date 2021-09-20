package com.duodevloopers.foodup.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duodevloopers.foodup.Adapter.BannerAdapter;
import com.duodevloopers.foodup.Adapter.PopularItemAdapter;
import com.duodevloopers.foodup.Adapter.RestaurantAdapter;
import com.duodevloopers.foodup.ViewModel.MainActivityViewModel;
import com.duodevloopers.foodup.Model.PopularItem;
import com.duodevloopers.foodup.Model.RestaurantPojo;
import com.duodevloopers.foodup.MyPageTransformer;
import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    private PopularItemAdapter popularItemAdapter;
    private FragmentHomeBinding binding;
    private RestaurantAdapter.RecyclerViewClickListener listener;
    private MainActivityViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
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

        int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (currentTime >= 0 && currentTime <= 5) {
            binding.textViewGreeting.setText("Good Night," + " Aidul");
            binding.textViewWhatYouWant.setText("Go to your bed and dreaming about FoodUp!");
        } else if (currentTime >= 6 && currentTime <= 10) {
            binding.textViewGreeting.setText("Good Morning," + " Aidul");
            binding.textViewWhatYouWant.setText("What's for breakfast? There are 20 restaurant in your area");
        } else if (currentTime >= 11 && currentTime <= 15) {
            binding.textViewGreeting.setText("Good Noon," + " Aidul");
            binding.textViewWhatYouWant.setText("What's for lunch? There are 20 restaurant in your area");
        } else if (currentTime >= 16 && currentTime <= 18) {
            binding.textViewGreeting.setText("Good Afternoon," + " Aidul");
            binding.textViewWhatYouWant.setText("Do you wanna have some Cha? There are 20 restaurant in your area");
        } else if (currentTime >= 19 && currentTime <= 20) {
            binding.textViewGreeting.setText("Good Evening," + " Aidul");
            binding.textViewWhatYouWant.setText("What's for snacks? There are 20 restaurant in your area");
        } else if (currentTime >= 21 && currentTime <= 24) {
            binding.textViewGreeting.setText("Good Night," + " Aidul");
            binding.textViewWhatYouWant.setText("What's for dinner? There are 20 restaurant in your area");
        }

        binding.bannerViewpager.setPageTransformer(new MyPageTransformer());
        binding.bannerViewpager.setAdapter(new BannerAdapter());
        binding.bannerViewpager.setOffscreenPageLimit(3);
        binding.bannerViewpager.setCurrentItem(1);

        ArrayList<RestaurantPojo> restaurantPojoArrayList = new ArrayList<>();
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food, "Kashbon", "Cafe and Hotel", "5.0"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food, "Allah'r Dan", "Bangla Hotel", "4.5"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food, "Central Cafeteria", "Cafe", "5.0"));
        restaurantPojoArrayList.add(new RestaurantPojo(R.drawable.food, "Mannan Hotel and Tea ", "Bangla Hotel", "5.0"));


        binding.homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RestaurantAdapter(restaurantPojoArrayList);


        List<PopularItem> popularItems = new ArrayList<>();
        popularItems.add(new PopularItem("Burger", "This is a good burger!", "250", R.drawable.ic_burger));
        popularItems.add(new PopularItem("Pizza", "This is a good pizza!", "450", R.drawable.ic_pizza));
        popularItems.add(new PopularItem("Sandwich", "This is a good sandwich!", "50", R.drawable.ic_sandwich));
        popularItems.add(new PopularItem("Biriyani", "This is a good biriyani!", "200", R.drawable.ic_biryani));
        popularItems.add(new PopularItem("Shingara", "This is a good shingara!", "5", R.drawable.ic_samosa));


        binding.popularItemRecyclerview.setHasFixedSize(true);
        popularItemAdapter = new PopularItemAdapter(popularItems);
        binding.popularItemRecyclerview.setAdapter(popularItemAdapter);

        setOnPopularItemClickListener();

        setOnCliskListener();

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.homeRecyclerView.setAdapter(adapter);


    }

    private void setOnCliskListener() {
        adapter.setListener(new RestaurantAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(String resName) {
                final NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                NavDirections action = (NavDirections) HomeFragmentDirections.actionHomeFragmentToRestaurantFragment(resName);
                navController.navigate(action);
            }
        });
    }

    private void setOnPopularItemClickListener() {
        popularItemAdapter.setPopularItemListener(new PopularItemAdapter.PopularItemRecyclerViewClickListener() {
            @Override
            public void onClick(PopularItem popularItem) {
                model.getPopularItemMutableLiveData().postValue(popularItem);
            }
        });
    }
}