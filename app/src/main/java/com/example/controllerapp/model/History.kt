package com.example.controllerapp.model

data class History (
    var kelembaban: Int,
    var suhu: Int,
    var key: String,
    var time: String,
    var date: String
){
    constructor() : this(0,0,"","","")
}