package com.duodevloopers.foodup.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duodevloopers.foodup.Constant.Constant;
import com.duodevloopers.foodup.databinding.FragmentEditUserProfileBinding;

import org.jetbrains.annotations.NotNull;

public class EditUserProfileFragment extends Fragment {
    private static final String TAG = "EditUserProfileFragment";
    private FragmentEditUserProfileBinding binding;
    private int selection;
    private String name,email,dept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            selection = getArguments().getInt(Constant.USER_EDIT_SELECTION);
            name = getArguments().getString(Constant.USER_NAME);
            email = getArguments().getString(Constant.USER_EMAIL);
            dept = getArguments().getString(Constant.USER_DEPT);
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
                //splitting the fullName to first and last name
                String fullName = name;
                int idx = fullName.lastIndexOf(' ');
                if (idx == -1)
                    throw new IllegalArgumentException("Only a single name: " + fullName);
                String firstName = fullName.substring(0, idx);
                String lastName  = fullName.substring(idx + 1);

                binding.firstTextInputLayout.setHint("First Name");
                binding.secondTextInputLayout.setHint("Last Name");
                binding.firstEdittext.setText(firstName);
                binding.secondEdittext.setText(lastName);
                binding.secondTextInputLayout.setVisibility(View.VISIBLE);
                break;

            case 1:
                binding.firstTextInputLayout.setHint("Email");
                binding.firstEdittext.setText(email);
                binding.secondTextInputLayout.setVisibility(View.GONE);
                break;

            case 2:
                binding.firstTextInputLayout.setHint("Department");
                binding.firstEdittext.setText(dept);
                binding.secondTextInputLayout.setVisibility(View.GONE);
                break;
        }
    }
}