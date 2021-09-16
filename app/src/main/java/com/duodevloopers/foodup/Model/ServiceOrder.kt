package com.duodevloopers.foodup.Model

class ServiceOrder(
    private var cost: String,
    private var done: Boolean,
    private var id: String,
    private var link: String,
    private var page: Double,
    private var paid: Boolean,
    private var timestamp: String,
    private var type: String

) {

    fun getCost(): String {
        return cost
    }

    fun isDone(): Boolean {
        return done
    }

    fun getLink(): String {
        return link
    }

    fun getId(): String {
        return id
    }

    fun getPage(): Int {
        return page.toInt()
    }

    fun isPaid(): Boolean {
        return paid
    }

    fun getTimestamp(): String {
        return timestamp
    }

    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setCost(cost: String) {
        this.cost = cost
    }

    fun setLink(link: String) {
        this.link = link
    }

}