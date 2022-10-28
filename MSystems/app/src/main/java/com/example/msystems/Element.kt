package com.example.msystems

class Element(description : String, day : Int, month : Int, year : Int = 2022) {
    var description = description
    var day = day
    var month = month
    var year = year

    fun getTime() : String{
        var t = ""
        t += if(day < 10) "0$day." else "$day."
        t += if(month < 10) "0$month." else "$month."
        t += "$year"
        if(year == 0) t = "время"
        return t
    }

    fun setTime(day : Int, month : Int, year : Int){
        this.day = day
        this.month = month
        this.year = year
    }

    fun delete() {
        description = ""
        day = 0
        month = 0
        year = 0
    }
}