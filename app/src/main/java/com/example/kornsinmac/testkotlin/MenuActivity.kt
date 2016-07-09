package com.example.kornsinmac.testkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    var busy = false
    var pref : SharedPreferences? = null

    override fun onCancelled(p0: DatabaseError?) {
        Toast.makeText(this@MenuActivity, p0!!.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDataChange(p0: DataSnapshot?) {
        if (p0!!.hasChildren()) {
            messages?.clear()
            p0.children.forEachIndexed { i, dataSnapshot ->
                messages?.add(dataSnapshot.getValue(Message::class.java))
            }
        } else {
            messages?.let {
                messages!!.clear()
            }
        }
        Collections.reverse(messages)
        adapter!!.notifyDataSetChanged()
        busy = false
    }

    var user: FirebaseUser? = null
    var messages: ArrayList<Message>? = arrayListOf()
    var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        pref = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        group = intent.getStringExtra("Group")

        user = FirebaseAuth.getInstance().currentUser
        if (user == null) startActivity(Intent(this, LoginActivity::class.java))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true

        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)
        scrollAction(recycler)

        adapter = messages?.let {
            MessageAdapter(this, it, this.user!!)
        }
        recycler.adapter = adapter

        msg.subscribe(this, group as String, 20)

        sendBtn.setOnClickListener {
            if (editBox.text.toString().length <= 0) return@setOnClickListener
            msg.sender = user!!.email.toString()
            msg.message = editBox.text.toString()
            msg.push()
            editBox.text.clear()
        }
    }

    fun scrollAction(recycler : RecyclerView){
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0){
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val last = layoutManager.findLastCompletelyVisibleItemPosition()
                    println(last)
                    if (last == (messages!!.size -1) && !busy) {
                        msg.subscribe(this@MenuActivity, group as String, messages!!.size + 20)
                        busy = true
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.sign_out){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        } else if (item?.itemId == R.id.leave_group) {
            pref!!.edit().putString("LAST_GROUP", null).commit()
            startActivity(Intent(this, GroupsActivity::class.java))
        }
        return true
    }

}
