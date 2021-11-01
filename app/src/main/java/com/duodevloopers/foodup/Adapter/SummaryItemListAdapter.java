package com.duodevloopers.foodup.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duodevloopers.foodup.Model.RestaurantItemPojo;
import com.duodevloopers.foodup.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SummaryItemListAdapter extends RecyclerView.Adapter<SummaryItemListAdapter.SummaryViewHolder>{

    List<RestaurantItemPojo> list = new ArrayList<>();

    public SummaryItemListAdapter(List<RestaurantItemPojo> list) {
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item_layout,parent,false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SummaryViewHolder holder, int position) {
        holder.quantity.setText(list.get(position).getQuantity()+"x");
        holder.name.setText(list.get(position).getmFoodName());
        holder.price.setText((list.get(position).getmFoodPrice() * list.get(position).getQuantity()) +" Tk");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SummaryViewHolder extends RecyclerView.ViewHolder{
        TextView quantity,name,price;
        public SummaryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.quantity);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
        }
    }


}
