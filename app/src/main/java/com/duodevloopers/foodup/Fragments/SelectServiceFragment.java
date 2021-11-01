package com.duodevloopers.foodup.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import com.duodevloopers.foodup.Activities.CaptureAct;
import com.duodevloopers.foodup.Activities.MainActivity;
import com.duodevloopers.foodup.Activities.MakeCoverPageActivity;
import com.duodevloopers.foodup.Activities.NoticeActivity;
import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.bottomsheet.PrintServiceBottomSheet;
import com.duodevloopers.foodup.bottomsheet.RoomBottomSheet;
import com.duodevloopers.foodup.callbacks.PrintBottomSheetInteractionCallback;
import com.duodevloopers.foodup.databinding.FragmentSelectServiceBinding;
import com.duodevloopers.foodup.utility.Constants;
import com.google.zxing.integration.android.IntentIntegrator;

public class SelectServiceFragment extends Fragment implements PrintBottomSheetInteractionCallback, MotionLayout.TransitionListener {

    private static final String TAG = "SelectServiceFragment";

    private FragmentSelectServiceBinding binding;

    private PrintServiceBottomSheet bottomSheet;
    private RoomBottomSheet roomBottomSheet;

    private MotionLayout creditMotionLayout;

    private TextView credit;

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

    private View.OnClickListener qrScanActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.attendance) {
                scanCode(Constants.ATTENDANCE_REQUEST_CODE);
            } else if (v.getId() == R.id.recharge) {
                scanCode(Constants.RECHARGE_REQUEST_CODE);
            }

        }
    };

    @Override
    public void onConfirm() {
        Toast.makeText(requireContext(), "Your print request has been posted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        if (progress >= 0.50) {
            credit.setText("1211.00");
        }
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        motionLayout.transitionToStart();
        if (currentId == R.id.start) credit.setText("Tap");
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        creditMotionLayout = binding.getRoot().findViewById(R.id.motion_layout);
        credit = creditMotionLayout.findViewById(R.id.text);

        creditMotionLayout.addTransitionListener(this);

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

        binding.recharge.setOnClickListener(qrScanActivity);
        binding.attendance.setOnClickListener(qrScanActivity);

    }

    private void scanCode(int requestCode) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(requireActivity());
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.initiateScan();
//        Intent intent = intentIntegrator.createScanIntent();
//        startActivityForResult(intent, requestCode);
    }
}