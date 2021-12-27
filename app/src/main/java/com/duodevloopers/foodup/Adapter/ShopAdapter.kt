package com.duodevloopers.foodup.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.duodevloopers.foodup.R

class ShopAdapter(
    context: Context,
    private val shopList: MutableList<String>
) : BaseAdapter() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return shopList.size
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.spinner_layout, null)
        val shopNameText = view.findViewById(R.id.shop_name) as TextView
        shopNameText.text = shopList[position]
        return view
    }
}