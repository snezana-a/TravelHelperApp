package com.example.travelhelperapp.model

data class User (
    var fullname: String,
    var age: String,
    var email: String
) {
    constructor(): this("","","")
}
