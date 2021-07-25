package com.duodevloopers.foodup.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duodevloopers.foodup.Model.Banner;
import com.duodevloopers.foodup.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    List<Banner> bannerList = new ArrayList();

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.banner_layout, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
//        Banner currentItem = bannerList.get(position);
//        holder.offerItemImage.setBackgroundResource(currentItem.getImage());
//        holder.limitedOffer.setText("Limited offer");
//        holder.offerQuantity.setText(currentItem.getOfferQuantity());
//        holder.offerLimit.setText(currentItem.getOfferLimit());
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        private TextView limitedOffer, offerQuantity, offerLimit;
        private ImageView offerItemImage;

        public BannerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            limitedOffer = itemView.findViewById(R.id.limited_offer);
            offerQuantity = itemView.findViewById(R.id.offer_quantity);
            offerLimit = itemView.findViewById(R.id.offer_limit);
            offerItemImage = itemView.findViewById(R.id.offer_item_image);
        }
    }
}

