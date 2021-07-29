package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duodevloopers.foodup.Adapter.FragmentAdapter;
import com.duodevloopers.foodup.databinding.FragmentRestaurantBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class RestaurantFragment extends Fragment {

    private FragmentRestaurantBinding binding;
    public static final String ARG_OBJECT = "object";
    public static String[] TAB_TITLES = new String[]{"Breakfast", "lunch","Snacks"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        binding = FragmentRestaurantBinding.inflate(getLayoutInflater());

        binding.viewPager2.setAdapter(new FragmentAdapter(this,RestaurantFragmentArgs.fromBundle(getArguments()).getRestaurantTitle()));

        new TabLayoutMediator(binding.restaurantTabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(TAB_TITLES[position]);
            }
        }).attach();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.restaurantName.setText(RestaurantFragmentArgs.fromBundle(getArguments()).getRestaurantTitle());
        Bundle args = getArguments();

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApp.getmRestaurantItemPojo().size() > 0){
                    final NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    NavDirections action = RestaurantFragmentDirections.actionRestaurantFragmentToCartFragment();
                    navController.navigate(action);
                }else {
                    Toast.makeText(requireContext(), "Your Cart is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}