package com.duodevloopers.foodup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duodevloopers.foodup.Adapter.RoomAdapter;
import com.duodevloopers.foodup.Model.Room;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RoomBottomSheet {
    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private Context context;
    private RecyclerView roomRecyclerView;
    private ChipGroup chipGroup;
    private RoomAdapter adapter;

    public RoomBottomSheet(Context context) {
        this.context = context;

        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.room_bottom_sheet, null);
    }

    public void showBottomSheet() {

        roomRecyclerView = bottomSheetView.findViewById(R.id.room_recycler_view);
        chipGroup = bottomSheetView.findViewById(R.id.chip_group);

        getRooms("cse-building");

        roomRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    getRooms(chip.getText().toString().toLowerCase() + "-" + "building");
                }
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.show();

    }

    private void getRooms(String building) {

        Query query = FirebaseFirestore.getInstance()
                .collection(building).orderBy("status");

        adapter = new RoomAdapter(
                new FirestoreRecyclerOptions.Builder<Room>()
                        .setQuery(query, Room.class)
                        .build()
        );

        roomRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
