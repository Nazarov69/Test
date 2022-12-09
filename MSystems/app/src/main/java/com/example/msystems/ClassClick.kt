package com.example.msystems

class ClassClick(click : Int) {
    private var click = click
    fun add() : Int{
        click++
        return click
    }
    fun cancel() : Int{
        click = 0
        return click
    }
    fun get() : Int{
        return click
    }
}