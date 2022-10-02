package com.narayan.sandesh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.narayan.sandesh.Adaptar.MessageAdaptor
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var senderUid: String
    private lateinit var receiverUid: String
    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String
    private lateinit var list: ArrayList<MassageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        database = FirebaseDatabase.getInstance()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        senderRoom = senderUid+receiverUid
        receiverRoom = receiverUid+senderUid

        list = ArrayList()

        chatSendButton.setOnClickListener {
            if(chatMessage.text.isEmpty()){
                Toast.makeText(this,"Your Massage is Empty ! 🤷‍♂️",Toast.LENGTH_SHORT).show()
            }else{
                val message = MassageModel(chatMessage.text.toString(),senderUid)
                val rendomKey = database.reference.push().key
                database.reference.child("chats").child(senderRoom).child("message").child(rendomKey!!).setValue(message).addOnSuccessListener {

                        database.reference.child("chats").child(receiverRoom).child("message").child(rendomKey!!).setValue(message).addOnSuccessListener {
                            chatMessage.text= null
                        }

                    }
            }
        }

        database.reference.child("chats").child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MassageModel::class.java)
                        if (data != null) {
                            list.add(data)
                        }
                    }
                    chatRecyclerview.adapter = MessageAdaptor(this@ChatActivity,list)

                }


                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity,"$error",Toast.LENGTH_SHORT).show()
                }

            })
    }
}