package com.duodevloopers.foodup.bottomsheet

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.callbacks.OTPInputBottomSheetInteractionCallback
import com.google.android.material.bottomsheet.BottomSheetDialog

class OTPInputBottomSheet(
    private val context: Context,
    private val OTPInputBottomSheetInteractionCallback: OTPInputBottomSheetInteractionCallback
) : View.OnClickListener {

    private val bottomSheetDialog: BottomSheetDialog =
        BottomSheetDialog(context, R.style.BottomSheetDialogTheme)

    private val bottomSheetView: View =
        LayoutInflater.from(context).inflate(R.layout.mobile_number_bottom_sheet, null)

    private val number: EditText = bottomSheetView.findViewById(R.id.et_phone_number)
    private val submit: Button = bottomSheetView.findViewById(R.id.btn_submit_phone_number)

    fun showBottomSheet() {
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.setCancelable(true)
        submit.setOnClickListener {
            if (number.text.toString().isNotEmpty()) {
                Log.d("BottomSheet", "onClick: " + number.text.toString())
                OTPInputBottomSheetInteractionCallback.onNumberSubmitted(number.text.toString())
                hideBottomSheet()
            }
        }
        bottomSheetDialog.show()
    }

    fun hideBottomSheet() = bottomSheetDialog.dismiss()

    override fun onClick(v: View?) {
        when (v?.id) {
            submit.id -> {

            }
        }
    }
}