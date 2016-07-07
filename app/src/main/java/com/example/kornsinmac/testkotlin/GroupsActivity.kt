package com.example.kornsinmac.testkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_groups.*

class GroupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)
        join_btn.setOnClickListener {
            if (group.text.toString().isEmpty()) return@setOnClickListener
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("Group", group.text.toString())
            startActivity(intent)
        }
    }
}
