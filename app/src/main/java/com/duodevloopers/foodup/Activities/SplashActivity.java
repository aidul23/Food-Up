package com.duodevloopers.foodup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.duodevloopers.foodup.Activities.SelectServiceActivity;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lottieAnimationView = findViewById(R.id.lottie);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.animate().setDuration(2000);
                Intent intent = new Intent(SplashActivity.this, SelectServiceActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }
}