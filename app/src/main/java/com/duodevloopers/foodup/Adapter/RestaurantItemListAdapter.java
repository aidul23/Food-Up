package com.duodevloopers.foodup.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.Model.RestaurantItemPojo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantItemListAdapter extends RecyclerView.Adapter<RestaurantItemListAdapter.RestaurantItemListViewHolder> {

    private ArrayList<RestaurantItemPojo> mRestaurantItemPojoArrayList;
    private RecyclerViewListItemClickListener listener;
    ArrayList<RestaurantItemPojo> selectedItem = new ArrayList<>();
    boolean isSelected = false;


    public class RestaurantItemListViewHolder extends RecyclerView.ViewHolder {
        public ImageView mFoodImageView;
        public TextView mFoodName, mFoodPrice;
        private CardView cardView;

        public RestaurantItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.restaurant_list_item_cardView);
            mFoodImageView = itemView.findViewById(R.id.foodImage);
            mFoodName = itemView.findViewById(R.id.foodName);
            mFoodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }

    public RestaurantItemListAdapter(ArrayList<RestaurantItemPojo> restaurantItemPojoArrayList) {
        this.mRestaurantItemPojoArrayList = restaurantItemPojoArrayList;
    }

    @NonNull
    @Override
    public RestaurantItemListAdapter.RestaurantItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_restaurant_item_list, parent, false);
        return new RestaurantItemListAdapter.RestaurantItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantItemListViewHolder holder, final int position) {
        RestaurantItemPojo currentItem = mRestaurantItemPojoArrayList.get(position);

        holder.mFoodImageView.setImageResource(currentItem.getmImage());
        holder.mFoodName.setText(currentItem.getmFoodName());
        holder.mFoodPrice.setText(String.format("%d Tk",currentItem.getmFoodPrice()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = true;
                if(isSelected){
                    if(selectedItem.contains(mRestaurantItemPojoArrayList.get(position))){
                        holder.cardView.setCardBackgroundColor(Color.WHITE);
                        holder.mFoodName.setTextColor(Color.BLACK);
                        holder.mFoodPrice.setTextColor(Color.rgb(254, 114, 76));
                        selectedItem.remove(mRestaurantItemPojoArrayList.get(position));
                    } else{
                        listener.onClick(mRestaurantItemPojoArrayList.get(position));
                        holder.cardView.setCardBackgroundColor(Color.rgb(255, 197, 41));
                        holder.mFoodName.setTextColor(Color.WHITE);
                        holder.mFoodPrice.setTextColor(Color.WHITE);
                        selectedItem.add(mRestaurantItemPojoArrayList.get(position));
                    }

                    if(selectedItem.size() == 0)
                        isSelected = false;
                }
                else {

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mRestaurantItemPojoArrayList.size();
    }

    public interface RecyclerViewListItemClickListener {
        void onClick(RestaurantItemPojo restaurantItemPojo);
    }

    public void setListener(RestaurantItemListAdapter.RecyclerViewListItemClickListener listener) {
        this.listener = listener;
    }

}
