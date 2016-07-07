package com.example.kornsinmac.testkotlin.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Message(var sender: String = "", var message: String = "") {

    var firebaseDb : FirebaseDatabase
    var msgRef: DatabaseReference? = null

    init {
        firebaseDb = FirebaseDatabase.getInstance()
    }

    fun subscribe(event : ValueEventListener, groupName : String){
        msgRef = firebaseDb.getReference("/messages/"+groupName+"/")
        msgRef!!.addValueEventListener(event)
    }

    fun push(groupName: String){
        msgRef = firebaseDb.getReference("/messages/"+groupName+"/")
        val haskmap = HashMap<String, Any>()
        haskmap.put("sender", this.sender)
        haskmap.put("message", this.message)
        val group = HashMap<String, Any>()
        group.put(msgRef!!.push().key, haskmap)
        msgRef!!.updateChildren(group) { p0, p1 -> }
    }

}
/**
 * Created by kornsin.mac on 4/7/2016 AD.
 */
