package com.duodevloopers.foodup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<RestaurantPojo> mRestaurantPojoArrayList;
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public ImageView mRestaurantImageView;
        public TextView mRestaurantName,mRestaurantCategory,mRestaurantRating;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            mRestaurantImageView = itemView.findViewById(R.id.thumbnail_restaurant);
            mRestaurantName = itemView.findViewById(R.id.title_restaurant);
            mRestaurantCategory = itemView.findViewById(R.id.category_restaurant);
            mRestaurantRating = itemView.findViewById(R.id.rating_restaurant_text);
        }
    }

    public RestaurantAdapter(ArrayList<RestaurantPojo> restaurantPojoArrayList){
        mRestaurantPojoArrayList = restaurantPojoArrayList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_home_recycler_view,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantPojo currentItem = mRestaurantPojoArrayList.get(position);

        holder.mRestaurantImageView.setImageResource(currentItem.getmImage());
        holder.mRestaurantName.setText(currentItem.getmRestaurantName());
        holder.mRestaurantCategory.setText(currentItem.getmRestaurantCategory());
        holder.mRestaurantRating.setText(currentItem.getmRestaurantRating());
    }


    @Override
    public int getItemCount() {
        return mRestaurantPojoArrayList.size();
    }
}
