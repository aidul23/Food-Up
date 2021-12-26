package com.duodevloopers.foodup.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.repository.Repository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

public class SelectServiceActivity extends AppCompatActivity {

    private static final String TAG = "SelectServiceActivity";

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

//        startActivity(new Intent(this, OrderStatusActivity.class));

        repository = new Repository();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {

                showSnackBar();

                String[] contents = intentResult.getContents().split("/");

                if (contents.length == 3) {
                    // recharge
                    recharge(contents[0]);
                } else if (contents.length == 2) {
                    // attendance
                    //repository.writeToSheet(MyApp.getLoggedInUser());

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(android.R.id.content), "QR Code successfully scanned", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.GRAY)
                .setActionTextColor(Color.WHITE)
                .show();
    }

    private void recharge(String amount) {
        FirebaseFirestore.getInstance()
                .runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(@NotNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentReference reference = FirebaseFirestore.getInstance().collection("users").document("C171080");
                        DocumentSnapshot documentSnapshot = transaction.get(reference);

                        double newAmount = Double.parseDouble(documentSnapshot.get("credit").toString()) + Double.parseDouble(amount);
                        transaction.update(reference, "credit", newAmount);

                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        });
    }
}