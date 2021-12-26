package com.duodevloopers.foodup.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import com.duodevloopers.foodup.Activities.CaptureAct
import android.content.Intent
import com.google.zxing.integration.android.IntentResult
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.duodevloopers.foodup.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.editTextId.setOnClickListener(this)

        binding.cirRegisterButton.setOnClickListener {
            if (binding.editTextName.text.toString() != "" &&
                binding.editTextDepartment.text.toString() != "" &&
                binding.editTextEmail.text.toString() != "" &&
                binding.editTextSection.text.toString() != "" &&
                binding.editTextId.text.toString() != ""
            ) {
                binding.animationView.visibility = View.VISIBLE
                val map: MutableMap<String, String> = HashMap()
                map["department"] = binding.editTextDepartment.text.toString()
                map["section"] = binding.editTextSection.text.toString()
                map["id"] = binding.editTextId.text.toString()
                map["name"] = binding.editTextName.text.toString()
                map["email"] = binding.editTextEmail.text.toString()
                map["credit"] = "0.00"
                map["type"] = "student"
                map["number"] = FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()
                FirebaseFirestore.getInstance()
                    .collection("student")
                    .document(FirebaseAuth.getInstance().currentUser?.phoneNumber.toString())
                    .set(map)
                    .addOnSuccessListener {
                        binding.animationView.visibility = View.GONE
                        startActivity(Intent(this,SelectServiceActivity::class.java))
                        finish()
//                        Toast.makeText(
//                            requireActivity(),
//                            "Registration successful",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.animationView.visibility = View.GONE
                    }

            } else {
                binding.animationView.visibility = View.GONE
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(view: View) {
        scanCode()
    }

    private fun scanCode() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.captureActivity = CaptureAct::class.java
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        intentIntegrator.setPrompt("Scanning Barcode from Your Student ID Card")
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents != null) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Scanning Result")
                builder.setMessage(intentResult.contents)
                builder.setPositiveButton("Scan Again") { dialogInterface, i -> scanCode() }
                    .setNegativeButton("Ok") { dialogInterface, i ->
                        binding!!.editTextId.setText(
                            intentResult.contents
                        )
                    }
                val dialog = builder.create()
                dialog.show()
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}