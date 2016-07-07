package com.example.kornsinmac.testkotlin.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Message(var sender: String = "", var message: String = "") {

    var firebaseDb : FirebaseDatabase
    var myRef : DatabaseReference

    init {
        firebaseDb = FirebaseDatabase.getInstance()
        myRef = firebaseDb!!.getReference("messages")
    }

    fun subscribe(event : ValueEventListener){
        myRef.addValueEventListener(event)
    }

    fun push(){
        val haskmap = HashMap<String, Any>()
        haskmap.put("sender", this.sender)
        haskmap.put("message", this.message)
        var group = HashMap<String, Any>()
        group.put(myRef.ref.push().key, haskmap)
        myRef.updateChildren(group) { p0, p1 -> }
    }

}
/**
 * Created by kornsin.mac on 4/7/2016 AD.
 */
