package com.duodevloopers.foodup.Model

class Notice(
    var date: String,
    var imageUrl: String,
    var section: String,
    var title: String,
    var type: String,
) {

    constructor() : this("", "", "", "", "")

    override fun toString(): String {
        return super.toString()
    }
}