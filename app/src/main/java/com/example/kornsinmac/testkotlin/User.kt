package com.example.kornsinmac.testkotlin

data class User(var age: Int = 0, var name: String = "") {
    init {
        fun copy(age: Int = this.age, name: String = this.name) = User(age, name)
    }
}
/**
 * Created by kornsin.mac on 4/7/2016 AD.
 */
