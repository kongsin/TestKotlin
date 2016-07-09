package com.example.kornsinmac.testkotlin.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Message(var sender: String = "", var message: String = "") {

    var firebaseDb : FirebaseDatabase? = null
    var msgRef: DatabaseReference? = null

    fun subscribe(event : ValueEventListener, groupName : String, limit : Int){
        firebaseDb = FirebaseDatabase.getInstance()
        msgRef = firebaseDb!!.getReference("/messages/"+groupName+"/")
        msgRef!!.limitToLast(limit).addValueEventListener(event)
    }

    fun push(){
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
