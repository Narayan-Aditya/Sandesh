package com.narayan.sandesh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.narayan.sandesh.Adaptar.ViewPagerAdaptor
import com.narayan.sandesh.Ui.CallFragment
import com.narayan.sandesh.Ui.ChatFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(CallFragment())
        val adapter = ViewPagerAdaptor(this,supportFragmentManager,fragmentArrayList)
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
        signOut.setOnClickListener {
            Toast.makeText(this@MainActivity,"SignOut 👍",Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
            startActivity(Intent(this@MainActivity,signin::class.java))
            finish()
        }
    }

}