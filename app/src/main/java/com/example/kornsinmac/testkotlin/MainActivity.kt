package com.example.kornsinmac.testkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email.hint = "Email"
        passWord.hint = "Password"
        clickMe.setOnClickListener({
            auth = FirebaseAuth.getInstance()
            auth!!.createUserWithEmailAndPassword(email.text.toString(), passWord.text.toString())
            .addOnCompleteListener(this, {
                task : Task<AuthResult> ->
                if (task.isSuccessful){
                    println("Success")
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    println("Failed")
                }
            })
        })
    }
}
