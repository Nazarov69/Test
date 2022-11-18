package com.example.msystems

class Elem_db(id : Int, text : String) {
    var text = text
    var index = id
    fun set(text : String){ this.text = text }
    fun get() : String { return text}
}