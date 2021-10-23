package com.duodevloopers.foodup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.duodevloopers.foodup.callbacks.PrintBottomSheetInteractionCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AssignmentBottomSheet {

    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private Context context;

    public AssignmentBottomSheet(Context context, PrintBottomSheetInteractionCallback callback) {
        this.context = context;

        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.assignment_bottom_sheet, null);
    }
}
