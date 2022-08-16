package com.example.travelhelperapp.Model

data class User (
    var fullname: String,
    var age: String,
    var email: String
) {
    constructor(): this("","","")
}
