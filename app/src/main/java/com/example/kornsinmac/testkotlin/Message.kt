package com.example.kornsinmac.testkotlin

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Message(var sender: String = "", var message: String, val event : ValueEventListener) {

    var firebaseDb = FirebaseDatabase.getInstance()
    val myRef = firebaseDb!!.getReference("messages")

    fun loadData(){
        myRef.addValueEventListener(event)
    }

    fun push(){
        myRef.addValueEventListener(event)
        val haskmap = HashMap<String, Any>()
        haskmap.put("sender", this.sender)
        haskmap.put("message", this.message)
        var group = HashMap<String, Any>()
        group.put(myRef.ref.push().key, haskmap)
        myRef.updateChildren(group, object : DatabaseReference.CompletionListener {
            override fun onComplete(p0: DatabaseError?, p1: DatabaseReference?) {

            }

        })
    }

}
/**
 * Created by kornsin.mac on 4/7/2016 AD.
 */
