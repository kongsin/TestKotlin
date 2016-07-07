package com.example.kornsinmac.testkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var user : FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            startActivity(Intent(this, GroupsActivity::class.java))
            finish()
        }
        email.hint = "Email"
        passWord.hint = "Password"
        signIn.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), passWord.text.toString()).addOnCompleteListener {
                task : Task<AuthResult> ->
                if (task.isSuccessful){
                    user = task.result.user
                    if (user != null){
                        startActivity(Intent(this, GroupsActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
