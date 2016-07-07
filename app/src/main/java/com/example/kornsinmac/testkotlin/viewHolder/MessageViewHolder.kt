package com.example.kornsinmac.testkotlin.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.example.kornsinmac.testkotlin.models.Message
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.message_layout.view.*

/**
 * Created by kongsin_mac on 7/7/2016 AD.
 */
class MessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun process(message : Message, currentUser : FirebaseUser){
        if (currentUser.email.equals(message.sender.toString())){
            itemView.rootContent.gravity = Gravity.RIGHT
        } else {
            itemView.rootContent.gravity = Gravity.LEFT
        }
        itemView.sender.text = message.sender
        itemView.message.text = message.message
    }
}