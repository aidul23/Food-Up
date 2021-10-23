package com.duodevloopers.foodup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duodevloopers.foodup.Activities.MainActivity;
import com.duodevloopers.foodup.Activities.NoticeActivity;
import com.duodevloopers.foodup.callbacks.PrintBottomSheetInteractionCallback;
import com.duodevloopers.foodup.databinding.FragmentSelectServiceBinding;

public class SelectServiceFragment extends Fragment implements PrintBottomSheetInteractionCallback {

    private FragmentSelectServiceBinding binding;

    private PrintServiceBottomSheet bottomSheet;
    private RoomBottomSheet roomBottomSheet;

    public SelectServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomSheet = new PrintServiceBottomSheet(requireContext(), this);
        roomBottomSheet = new RoomBottomSheet(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectServiceBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        binding.page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeCoverPageActivity.class);
                startActivity(intent);
            }
        });

        binding.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.showBottomSheet();
            }
        });

        binding.room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomBottomSheet.showBottomSheet();
            }
        });

        binding.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), NoticeActivity.class));
            }
        });
    }

    @Override
    public void onConfirm() {
        Toast.makeText(requireContext(), "Your print request has been posted", Toast.LENGTH_SHORT).show();
    }
}