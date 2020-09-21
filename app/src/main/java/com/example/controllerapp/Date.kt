package com.example.controllerapp

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class Date {

    fun getDate(): String{
        var dateFormat: DateFormat = SimpleDateFormat("EEE,dd MMMM yyyy")
        var date = Date()
        return dateFormat.format(date)
    }

    fun getTime(): String{
        var dateFormat: DateFormat = SimpleDateFormat("h:mm a")
        var data = Date()
        return dateFormat.format(data)
    }
}