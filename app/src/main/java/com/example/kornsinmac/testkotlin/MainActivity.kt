package com.example.kornsinmac.testkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userName.hint = "UserName"
        passWord.hint = "Password"
        clickMe.text = "Login"
        clickMe.setOnClickListener({
            println("user name : " + userName.text)
            println("password : " + passWord.text)
            if (userName.text.toString().equals("kongsin") && passWord.text.toString().equals("12345")) {
                Toast.makeText(this, "Log in success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong login", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
