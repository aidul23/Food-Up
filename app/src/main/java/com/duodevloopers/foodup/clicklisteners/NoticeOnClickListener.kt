package com.duodevloopers.foodup.clicklisteners

import com.duodevloopers.foodup.Model.Notice

interface NoticeOnClickListener {

    fun onViewPhoto(url: String)
    fun onViewDoc(url: String)
    fun onViewNotice(model: Notice)

}