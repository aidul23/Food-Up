package com.duodevloopers.foodup.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.duodevloopers.foodup.R

class OngoingOrderDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ongoing_order_dialog_layout)
    }

}