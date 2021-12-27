package com.duodevloopers.foodup.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.myapp.MyApp;
import com.duodevloopers.foodup.repository.Repository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SelectServiceActivity extends AppCompatActivity {

    private static final String TAG = "SelectServiceActivity";

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

//        startActivity(new Intent(this, OrderStatusActivity.class));

        obserForOngoingOrders();

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
                    if (contents[0].equals(MyApp.Companion.getLoggedInUser().id)) {
                        recharge(contents[1]);
                    } else {
                        Toast.makeText(this, "ID did not match", Toast.LENGTH_SHORT).show();
                    }
                } else if (contents.length == 2) {
                    // attendance
                    repository.writeToSheet(MyApp.Companion.getLoggedInUser(), contents[0]);

                }

                Log.d(TAG, "onActivityResult: " + Arrays.toString(contents));

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
                        DocumentReference reference = FirebaseFirestore.getInstance().collection("student").document(MyApp.Companion.getLoggedInUser().number);
                        DocumentSnapshot documentSnapshot = transaction.get(reference);

                        Double newAmount = Double.parseDouble(documentSnapshot.get("credit").toString()) + Double.parseDouble(amount);
                        transaction.update(reference, "credit", newAmount.toString());

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

    private void obserForOngoingOrders() {

        String lastOrderShop = getSharedPreferences("shop", MainActivity.MODE_PRIVATE)
                .getString("shop_number", "");

        if (lastOrderShop != null && !lastOrderShop.equals("")) {
            FirebaseFirestore.getInstance()
                    .collection("shops")
                    .document(lastOrderShop)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            DocumentReference ref = documentSnapshot.getReference();
                            ref.collection("orders")
                                    .document(MyApp.Companion.getLoggedInUser().id)
                                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (!value.getBoolean("done")) {
                                                new AlertDialog.Builder(SelectServiceActivity.this)
                                                        .setTitle("Ongoing Order")
                                                        .setMessage("Please do not place any order. You have an ongoing order")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", (dialog, which) -> dialog.cancel());
                                            } else {
                                                getSharedPreferences("shop", MainActivity.MODE_PRIVATE)
                                                        .edit().clear().apply();
                                            }
                                        }
                                    });
                        }
                    });
        }

    }
}