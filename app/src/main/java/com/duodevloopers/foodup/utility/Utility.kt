package com.duodevloopers.foodup.utility

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Utility {
    companion object {

        val priceMap: HashMap<String, Int> = HashMap()

        fun getPrice(type: String): Int {
            priceMap["Normal Paper B&W"] = 5
            priceMap["Normal Paper Color"] = 10
            priceMap["Offset Paper B&W"] = 7
            priceMap["Offset Paper Color"] = 12
            return priceMap[type]!!
        }

        fun getQuery(type: String, section: String): Query {
            return FirebaseFirestore.getInstance()
                .collection("notice")
                .whereEqualTo("type", type)
                .whereEqualTo("section", section)
        }

        fun showToast(context: Context, s: String) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }
}