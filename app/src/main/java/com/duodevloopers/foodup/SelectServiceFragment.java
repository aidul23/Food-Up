package com.duodevloopers.foodup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.duodevloopers.foodup.callbacks.PrintBottomSheetInteractionCallback;

public class SelectServiceFragment extends Fragment implements PrintBottomSheetInteractionCallback {


    public SelectServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_service, container, false);
    }

    @Override
    public void onConfirm() {
        Toast.makeText(requireContext(), "Your print request has been posted", Toast.LENGTH_SHORT).show();
    }
}