package com.duodevloopers.foodup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;

public class RoomBottomSheet {
    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private Context context;
    private RecyclerView roomRecyclerView;
    private ChipGroup chipGroup;

    public RoomBottomSheet(Context context) {
        this.context = context;

        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.room_bottom_sheet, null);
    }

    public void showBottomSheet() {

        roomRecyclerView = bottomSheetView.findViewById(R.id.room_recycler_view);
        chipGroup = bottomSheetView.findViewById(R.id.chip_group);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4, LinearLayoutManager.HORIZONTAL, false);
        roomRecyclerView.setLayoutManager(gridLayoutManager);


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

            }
        });


    }
}
