package com.duodevloopers.foodup.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.duodevloopers.foodup.Adapter.CartAdapter;
import com.duodevloopers.foodup.Adapter.SummaryItemListAdapter;
import com.duodevloopers.foodup.Model.FoodOrder;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.Model.SummaryItemPojo;
import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.databinding.FragmentSummaryBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SummaryFragment extends Fragment implements View.OnClickListener{

    private FragmentSummaryBinding binding;
    private SummaryItemListAdapter adapter;
    @Override
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
        binding = FragmentSummaryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SummaryFragmentArgs args = SummaryFragmentArgs.fromBundle(getArguments());
        SummaryItemPojo summaryItemPojo = args.getSummaryItempojo();

        String subTotal = args.getSubtotal();
        binding.subTotalAmount.setText(subTotal+" Tk");
        binding.discountAmount.setText("-30 Tk");
        if(Integer.valueOf(subTotal) >= 50){
            binding.totalAmount.setText(Integer.valueOf(subTotal) - 30+" Tk");
        }

        String text  = "By proceeding you are agreeing to our Terms and Conditions";
        SpannableString string = new SpannableString(text);

        ForegroundColorSpan primaryColor = new ForegroundColorSpan(Color.RED);

        string.setSpan(primaryColor,38,58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.text.setText(string);


        adapter = new SummaryItemListAdapter(summaryItemPojo.getList());
        binding.summaryItemRecyclerView.setAdapter(adapter);
        binding.summaryItemRecyclerView.setHasFixedSize(true);

        binding.cashPayment.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_border));
        binding.digitalPayment.setOnClickListener(this);
        binding.cashPayment.setOnClickListener(this);

        binding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                NavDirections action = SummaryFragmentDirections.actionSummaryFragmentToOrderStatusFragment();
                navController.navigate(action);
                placeFoodOrder(summaryItemPojo.getList());
            }
        });
    }

    private void placeFoodOrder(List<RestaurantItemPojo> list) {
        FoodOrder order = new FoodOrder(
                "",
                "",
                false,
                "",
                "",
                false,
                String.valueOf(System.currentTimeMillis())
        );

        int totalCost = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (RestaurantItemPojo s : list) {
            stringBuilder.append(s.getmFoodName());
            stringBuilder.append("#");
            totalCost += s.getmFoodPrice();
        }

        order.setItem(stringBuilder.toString());
        order.setCost(String.valueOf(totalCost));

    }


    @Override
    public void onClick(View v) {
        binding.digitalPayment.setBackground(null);
        binding.cashPayment.setBackground(null);

        if (v.getId() == R.id.digital_payment) {
            binding.digitalPayment.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_border));
        } else if (v.getId() == R.id.cash_payment) {
            binding.cashPayment.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.button_border));
        }
    }
}