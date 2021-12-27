package com.duodevloopers.foodup.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.duodevloopers.foodup.R

class ViewImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        val imageUrl = intent.getStringExtra("url")

        val imageView = findViewById<ImageView>(R.id.image_view)

        Glide.with(this).load(imageUrl).into(imageView)
    }
}