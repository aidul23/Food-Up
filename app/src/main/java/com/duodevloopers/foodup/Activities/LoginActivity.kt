package com.duodevloopers.foodup.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.duodevloopers.foodup.bottomsheet.OTPInputBottomSheet
import com.duodevloopers.foodup.callbacks.OTPInputBottomSheetInteractionCallback
import com.duodevloopers.foodup.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() , OTPInputBottomSheetInteractionCallback {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationCode: String
    private lateinit var otpInputBottomSheet: OTPInputBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        otpInputBottomSheet = OTPInputBottomSheet(this, this)

        if (auth.currentUser != null) {
            startActivity(Intent(this,SelectServiceActivity::class.java))
            finish()
        }

        binding.cirLoginButton.setOnClickListener {

            if (binding.editTextPhone.text.isNotEmpty()) {

                binding.animationView.visibility = View.VISIBLE

                val number = "+88" + binding.editTextPhone.text.toString()
                sendVerificationCode(number)

            } else {
                Toast.makeText(
                    this,
                    "Please provide a valid phone number",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun sendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            this,
            callback
        )
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            TODO("Not yet implemented")
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            binding.animationView.visibility = View.GONE
            verificationCode = p0
            otpInputBottomSheet.showBottomSheet()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            binding.animationView.visibility = View.GONE
            Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onNumberSubmitted(number: String) {
        binding.animationView.visibility = View.VISIBLE
        val credential = PhoneAuthProvider.getCredential(verificationCode, number)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.animationView.visibility = View.GONE
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    checkIfStudentExists()
                } else {
                    binding.animationView.visibility = View.GONE
                    Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun checkIfStudentExists() {

        val number = FirebaseAuth.getInstance().currentUser?.phoneNumber

        FirebaseFirestore.getInstance()
            .collection("student")
            .get()
            .addOnSuccessListener {
                for (teacher in it.documents) {
                    if (teacher["number"] == number) {
                        binding.animationView.visibility = View.GONE
                        startActivity(Intent(this,SelectServiceActivity::class.java))
                        finish()
                        return@addOnSuccessListener
                    }
                }

                binding.animationView.visibility = View.GONE
                startActivity(Intent(this,RegistrationActivity::class.java))
                finish()
            }

    }
}