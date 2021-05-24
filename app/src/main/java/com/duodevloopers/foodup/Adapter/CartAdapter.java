package com.duodevloopers.foodup.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.Model.RestaurantPojo;
import com.duodevloopers.foodup.MyApp;
import com.duodevloopers.foodup.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<RestaurantItemPojo> myAppArrayList;

    public CartAdapter(List<RestaurantItemPojo> myAppArrayList) {
        this.myAppArrayList = myAppArrayList;
        Log.d(TAG, "CartAdapter: " + myAppArrayList.size());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_cart_recycler_view, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {
        final RestaurantItemPojo currentItem = myAppArrayList.get(position);

        holder.mFoodImageView.setImageResource(currentItem.getmImage());
        holder.mfoodName.setText(currentItem.getmFoodName());
        holder.mFoodPrice.setText(String.format("%d Tk", (currentItem.getmFoodPrice())));
        holder.mElegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                holder.mFoodPrice.setText(String.format("%d Tk", (currentItem.getmFoodPrice() * newValue)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myAppArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView mFoodImageView;
        public TextView mfoodName, mFoodPrice;
        public ElegantNumberButton mElegantNumberButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            mFoodImageView = itemView.findViewById(R.id.foodItemImage);
            mfoodName = itemView.findViewById(R.id.foodItemName);
            mFoodPrice = itemView.findViewById(R.id.foodItemPrice);
            mElegantNumberButton = itemView.findViewById(R.id.elegantNumberButton_quantity);
        }
    }

}
