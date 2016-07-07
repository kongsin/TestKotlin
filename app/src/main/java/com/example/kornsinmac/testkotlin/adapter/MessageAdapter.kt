package com.example.kornsinmac.testkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kornsinmac.testkotlin.Message
import com.example.kornsinmac.testkotlin.R
import com.example.kornsinmac.testkotlin.viewHolder.MessageViewHolder
import com.google.firebase.auth.FirebaseUser
import java.util.*

/**
 * Created by kongsin_mac on 7/7/2016 AD.
 */
class MessageAdapter(var context : Context,var messages : ArrayList<Message>, var user : FirebaseUser) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        holder!!.process(messages[position], user)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessageViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

}