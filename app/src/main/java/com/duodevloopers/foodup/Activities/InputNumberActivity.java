package com.duodevloopers.foodup.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.duodevloopers.foodup.LoadingDialog;
import com.duodevloopers.foodup.databinding.ActivityInputNumberBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class InputNumberActivity extends AppCompatActivity {

    private static final String TAG = "InputNumberActivity";
    private ActivityInputNumberBinding binding;
    private String userPhoneNumber = null;
    private FirebaseAuth mAuth;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingDialog = new LoadingDialog(InputNumberActivity.this);
        mAuth = FirebaseAuth.getInstance();

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.editTextNumber.getText().toString().trim().isEmpty()) {
                    if (binding.editTextNumber.getText().toString().trim().length() < 11) {
                        binding.editTextNumber.setError("Input a valid number!");
                    } else {
                        userPhoneNumber = "+88" + binding.editTextNumber.getText().toString().trim();

                        hideKeyboardFrom();
                        loadingDialog.startLoadingDialog();

                        sendVerificationCode(userPhoneNumber);
                    }
                } else {
                    binding.editTextNumber.setError("Input your mobile number!");
                }
            }
        });

        binding.editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    hideKeyboardFrom();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void sendVerificationCode(String phone) {
        Log.d(TAG, "sendVerificationCode: ");

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(InputNumberActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //signInWithCredential(phoneAuthCredential);//TODO research
            Log.d(TAG, "onVerificationCompleted: ");

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            if (s != null) {
                loadingDialog.dismissDialog();
                Log.d(TAG, "onCodeSent: " + s);
                Intent intent = new Intent(InputNumberActivity.this, OTPActivity.class);
                intent.putExtra("otp",s);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: " + e.getMessage());
        }
    };



    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextNumber.getWindowToken(), 0);
    }
}