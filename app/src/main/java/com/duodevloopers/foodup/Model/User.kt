package com.duodevloopers.foodup.Model

class User {

    lateinit var name: String
    lateinit var number: String
    lateinit var type: String
    lateinit var id: String
    lateinit var department: String
    lateinit var section: String
    lateinit var credit: String


    constructor()


    constructor(
        id: String,
        department: String,
        name: String,
        type: String,
        section: String,
        number: String,
        credit: String
    ) {
        this.name = name
        this.department = department
        this.type = type
        this.section = section
        this.number = number
        this.id = id
        this.credit = credit
    }

}