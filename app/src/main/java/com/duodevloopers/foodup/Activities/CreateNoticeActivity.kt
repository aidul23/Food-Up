package com.duodevloopers.foodup.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.duodevloopers.foodup.Model.Notice
import com.duodevloopers.foodup.databinding.ActivityCreateNoticeBinding
import com.duodevloopers.foodup.myapp.MyApp
import com.duodevloopers.foodup.utility.Utility
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class CreateNoticeActivity : AppCompatActivity() {

    private val TAG = "CreateNoticeActivity"

    private lateinit var notice: Notice

    private lateinit var binding: ActivityCreateNoticeBinding

    private lateinit var noticeType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        noticeType = intent.getStringExtra("type")!!

        notice = Notice(
            Utility.formatMillisecondsIntoDate(System.currentTimeMillis()),
            "",
            MyApp.getLoggedInUser().section,
            "",
            noticeType
        )

        binding.publish.setOnClickListener(View.OnClickListener {

            if ("" == binding.noticeTitle.text.toString()) {
                binding.noticeTitle.error = "Please provide a proper title"
            } else if (noticeType == "file" || noticeType == "image" && binding.noticeLink.equals("")) {
                binding.noticeLink.error = "Please provide a link"
            } else {
                publishNotice()
            }

        })

    }

    private fun publishNotice() {

        notice.title = binding.noticeTitle.text.toString()
        notice.imageUrl = binding.noticeLink.text.toString()

        FirebaseFirestore.getInstance()
            .collection("notice")
            .document(System.currentTimeMillis().toString())
            .set(notice)
            .addOnSuccessListener(OnSuccessListener {
                // TODO: 02/11/2021 make a success dialog
                Utility.showToast(this, "Published successfully")
            })

    }


}