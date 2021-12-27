package com.duodevloopers.foodup.Model

class Partner{

    lateinit var details: String
    lateinit var image: String
    lateinit var name: String
    lateinit var ownerName: String
    lateinit var phoneNumber: String
    lateinit var type: String

    constructor()

    constructor(
        details: String,
        image: String,
        name: String,
        ownerName: String,
        phoneNumber: String,
        type: String
    ) {
        this.details = details
        this.image = image
        this.name = name
        this.ownerName = ownerName
        this.phoneNumber = phoneNumber
        this.type = type
    }
}