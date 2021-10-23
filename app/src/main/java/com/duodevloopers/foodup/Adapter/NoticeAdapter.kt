package com.duodevloopers.foodup.Adapter

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

class NoticeAdapter : FirestoreRecyclerAdapter<Notice, NoticeAdapter.NoticeViewHolder> {

    private lateinit var type: String

    private lateinit var noticeOnClickListener: NoticeOnClickListener

    constructor(options: FirestoreRecyclerOptions<Notice>, type: String) : super(options) {
        this.type = type
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeViewHolder {

        val view = when (viewType) {
            0 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo_layout, parent, false)
            1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_doc_layout, parent, false)
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
        holder.title.text = model.title
        holder.date.text = model.date

        if (type == "photo") {
            Glide.with(holder.image.context).load(model.imageUrl).into(holder.image)
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            when (model.type) {
                "photo" -> {
                    noticeOnClickListener.onViewPhoto(model.imageUrl)
                }
                "doc" -> {
                    noticeOnClickListener.onViewDoc(model.imageUrl)
                }
                else -> {
                    noticeOnClickListener.onViewNotice(model)
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {

        when (type) {
            "photo" -> return 0
            "doc" -> return 1
        }

        return 2
    }

    class NoticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val date: TextView = view.findViewById(R.id.date)
        val image: ImageView = view.findViewById(R.id.image)
    }

    fun setNoticeOnClickListener(noticeOnClickListener: NoticeOnClickListener) {
        this.noticeOnClickListener = noticeOnClickListener
    }
}