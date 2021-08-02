package com.duodevloopers.foodup.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duodevloopers.foodup.Model.PopularItem;
import com.duodevloopers.foodup.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularItemAdapter extends RecyclerView.Adapter<PopularItemAdapter.PopularItemViewHolder> {

    List<PopularItem> list;

    private PopularItemRecyclerViewClickListener listener;

    public PopularItemAdapter(List<PopularItem> list) {
        this.list = list;
    }

    public void setPopularItemListener(PopularItemRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public PopularItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_popular_layout,parent,false);
        return new PopularItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularItemAdapter.PopularItemViewHolder holder, int position) {
        holder.foodItemName.setText(list.get(position).getFoodName());
        holder.foodItemDes.setText(list.get(position).getFoodDes());
        holder.foodItemPrice.setText(list.get(position).getFoodPrice()+" Tk");
        holder.foodItemImage.setImageResource(list.get(position).getFoodImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularItemViewHolder extends RecyclerView.ViewHolder{
        private TextView foodItemName,foodItemDes,foodItemPrice;
        private ShapeableImageView foodItemImage;

        public PopularItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            foodItemName = itemView.findViewById(R.id.food_name);
            foodItemDes = itemView.findViewById(R.id.food_description);
            foodItemPrice = itemView.findViewById(R.id.food_price);
            foodItemImage = itemView.findViewById(R.id.food_image);
        }
    }

    public interface PopularItemRecyclerViewClickListener{
        void onClick(PopularItem popularItem);
    }
}
