package com.narayan.sandesh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.InputEmail
import kotlinx.android.synthetic.main.activity_signin.InputPassword
import kotlinx.android.synthetic.main.activity_signup.*

class signin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()
        NewAccount.setOnClickListener {
            startActivity(Intent(this@signin,signup::class.java))
            finish()
        }
        ButtonSignin.setOnClickListener {
            val email = InputEmail.text.toString()
            val password = InputPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty())            {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@signin,"User $",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@signin,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@signin,"User Not Found !",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this@signin,"Error !🤦‍♂",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this@signin,MainActivity::class.java))
            finish()
        }
    }
}