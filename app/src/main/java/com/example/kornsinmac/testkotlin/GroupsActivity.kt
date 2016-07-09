package com.example.kornsinmac.testkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_groups.*

class GroupsActivity : AppCompatActivity() {
    var pref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)

        pref = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        val lastGroup = pref!!.getString("LAST_GROUP", null)
        println(lastGroup)
        if (lastGroup != null){
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("Group", lastGroup)
            startActivity(intent)
        }

        join_btn.setOnClickListener {
            if (group.text.toString().isEmpty()) return@setOnClickListener
            pref!!.edit().putString("LAST_GROUP", group.text.toString()).commit()
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("Group", group.text.toString())
            startActivity(intent)
        }
    }
}
