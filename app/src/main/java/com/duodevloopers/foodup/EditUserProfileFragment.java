package com.duodevloopers.foodup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duodevloopers.foodup.databinding.FragmentEditUserProfileBinding;

import org.jetbrains.annotations.NotNull;

public class EditUserProfileFragment extends Fragment {
    private FragmentEditUserProfileBinding binding;
    private int selection;
    private String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            selection = getArguments().getInt(Constant.USER_EDIT_SELECTION);
//            email = getArguments().getString(Constant.USER_EMAIL);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditUserProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (selection) {
            case 0:
                binding.firstTextInputLayout.setHint("First Name");
                binding.secondTextInputLayout.setHint("Last Name");
                binding.secondTextInputLayout.setVisibility(View.VISIBLE);
                break;

            case 1:
                binding.firstTextInputLayout.setHint("Email");
//                binding.firstEdittext.setText(email);
                binding.secondTextInputLayout.setVisibility(View.GONE);
                break;

            case 2:
                binding.firstTextInputLayout.setHint("Department");
                binding.secondTextInputLayout.setVisibility(View.GONE);
                break;
        }
    }
}