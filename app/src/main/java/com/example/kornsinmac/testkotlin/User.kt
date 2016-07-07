package com.example.kornsinmac.testkotlin

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class User(var email: String = "", var password: String, val event : ValueEventListener) {

    var firebaseDb = FirebaseDatabase.getInstance()
    fun save(){
        val myRef = firebaseDb!!.getReference("users")
        myRef.addValueEventListener(event)
        val haskmap = HashMap<String, Any>()
        haskmap.put("username", this.email)
        haskmap.put("password", this.password)
        myRef.setValue(haskmap)
    }

}
/**
 * Created by kornsin.mac on 4/7/2016 AD.
 */
