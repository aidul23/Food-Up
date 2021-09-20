package com.duodevloopers.foodup

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
    }
}