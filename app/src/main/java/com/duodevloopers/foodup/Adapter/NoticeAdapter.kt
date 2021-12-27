package com.duodevloopers.foodup.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duodevloopers.foodup.Model.Notice
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.clicklisteners.NoticeOnClickListener
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NoticeAdapter(options: FirestoreRecyclerOptions<Notice>, var type: String) :
    FirestoreRecyclerAdapter<Notice, NoticeAdapter.NoticeViewHolder>(options) {

    private val TAG = "NoticeAdapter"

    private lateinit var noticeOnClickListener: NoticeOnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeViewHolder {

        Log.d(TAG, "onCreateViewHolder: ")

        val view = when (viewType) {
            0 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo_layout, parent, false)
            1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_doc_layout, parent, false)
            3 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_layout, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notice_layout, parent, false)
        }

        return NoticeViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: NoticeViewHolder,
        position: Int,
        model: Notice
    ) {
        Log.d(TAG, "onBindViewHolder: ")

        holder.title.text = model.title
        holder.date.text = model.date

        if (type == "image" || type == "news") {
            Glide.with(holder.image!!.context).load(model.imageUrl).into(holder.image!!)
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            when (model.type) {
                "image" -> {
                    noticeOnClickListener.onViewPhoto(model.imageUrl)
                }
                "doc" -> {
                    noticeOnClickListener.onViewDoc(model.imageUrl)
                }
                "news" -> {
                    noticeOnClickListener.onViewNotice(model)
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {

        Log.d(TAG, "getItemViewType: " + type)

        when (type) {
            "image" -> return 0
            "doc" -> return 1
            "news" -> return 3
        }

        return 2
    }

    class NoticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val date: TextView = view.findViewById(R.id.date)
        val image: ImageView? = view.findViewById(R.id.image)
    }

    fun setNoticeOnClickListener(noticeOnClickListener: NoticeOnClickListener) {
        this.noticeOnClickListener = noticeOnClickListener
    }
}