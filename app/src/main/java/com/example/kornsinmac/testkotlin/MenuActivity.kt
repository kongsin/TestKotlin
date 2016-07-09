package com.example.kornsinmac.testkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kornsinmac.testkotlin.adapter.MessageAdapter
import com.example.kornsinmac.testkotlin.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.*

class MenuActivity : AppCompatActivity(), ValueEventListener {
    val msg: Message = Message()
    var group : String? = null

    override fun onCancelled(p0: DatabaseError?) {
        Toast.makeText(this@MenuActivity, p0!!.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDataChange(p0: DataSnapshot?) {
        if (p0!!.hasChildren()) {
            if (messages!!.size == 0) {
                p0.children.iterator().forEach {
                    msg ->
                    messages!!.add(msg.getValue(Message::class.java))
                }
            } else {
                messages!!.add(p0.children.last().getValue(Message::class.java))
            }
        } else {
            messages?.let {
                messages!!.clear()
            }
        }
        adapter!!.notifyDataSetChanged()
        val lm = recycler.layoutManager as LinearLayoutManager
        if(lm.findLastVisibleItemPosition() == adapter!!.itemCount-2){
            recycler.layoutManager.scrollToPosition(adapter!!.itemCount - 1)
        }
    }

    var user: FirebaseUser? = null
    var messages: ArrayList<Message>? = arrayListOf()
    var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        group = intent.getStringExtra("Group")

        user = FirebaseAuth.getInstance().currentUser
        if (user == null) startActivity(Intent(this, LoginActivity::class.java))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)

        adapter = messages?.let {
            MessageAdapter(this, it, this.user!!)
        }
        recycler.adapter = adapter

        msg.subscribe(this, group as String)

        sendBtn.setOnClickListener {
            if (editBox.text.toString().length <= 0) return@setOnClickListener
            msg.sender = user!!.email.toString()
            msg.message = editBox.text.toString()
            msg.push(group as String)
            editBox.text.clear()
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
