package com.example.kornsinmac.testkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kornsinmac.testkotlin.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.*

class MenuActivity : AppCompatActivity(), ValueEventListener {

    override fun onCancelled(p0: DatabaseError?) {
        Toast.makeText(this@MenuActivity, p0!!.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDataChange(p0: DataSnapshot?) {
        messages?.clear()
        p0?.children?.forEach {
            msg ->
            val tempMsg = Message(msg.child("sender").value.toString(), msg.child("message").value.toString(), this)
            messages?.add(tempMsg)
        }
        adapter?.notifyDataSetChanged()
    }

    var user : FirebaseUser? = null
    var messages : ArrayList<Message>? = arrayListOf()
    var adapter : MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        user = FirebaseAuth.getInstance().currentUser
        if (user == null) startActivity(Intent(this, LoginActivity::class.java))

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)

        adapter = messages?.let {
            MessageAdapter(this, it, this.user!!)
        }
        recycler.adapter = adapter

        val msg = Message(user!!.email.toString(), message.text.toString(), this)
        msg.loadData()

        sendBtn.setOnClickListener {
            if (message.text.length <= 0) return@setOnClickListener
            msg.sender = user!!.email.toString()
            msg.message = message.text.toString()
            msg.push()
            message.text.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        return true
    }

}
