package com.duodevloopers.foodup.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.duodevloopers.foodup.Adapter.SummaryItemListAdapter
import com.duodevloopers.foodup.Model.RestaurantItemPojo
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.databinding.ActivityOrderStatusBinding
import java.util.*

class OrderStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val restaurantItemPojoArrayList = ArrayList<RestaurantItemPojo>()


        restaurantItemPojoArrayList.add(
            RestaurantItemPojo(
                R.drawable.food_icon,
                "White rice & Chicken ",
                "One plate rice, one piece chicken with gravy",
                50,
                1
            )
        )
        restaurantItemPojoArrayList.add(
            RestaurantItemPojo(
                R.drawable.food_icon,
                "Khichuri & Chicken",
                "One plate khichuri, one piece chicken with gravy",
                60,
                1
            )
        )
        restaurantItemPojoArrayList.add(
            RestaurantItemPojo(
                R.drawable.food_icon,
                "Chicken Biriyani",
                "One plate biriyani rice, one piece chicken with gravy",
                80,
                1
            )
        )
        restaurantItemPojoArrayList.add(
            RestaurantItemPojo(
                R.drawable.food_icon,
                "Mineral Water",
                "Half liter",
                15,
                1
            )
        )

        val adapter = SummaryItemListAdapter(restaurantItemPojoArrayList)
        binding.summaryItemRecyclerView.adapter = adapter
        binding.summaryItemRecyclerView.setHasFixedSize(true)
    }
}