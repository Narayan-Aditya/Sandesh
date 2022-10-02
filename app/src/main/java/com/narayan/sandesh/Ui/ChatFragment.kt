package com.narayan.sandesh.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.narayan.sandesh.Adaptar.ChatAdaptor
import com.narayan.sandesh.UserDataModel
import com.narayan.sandesh.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    private var database: FirebaseDatabase? = null
    lateinit var userList: ArrayList<UserDataModel>

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstantiationState: Bundle?
    ):View{
        binding = FragmentChatBinding.inflate(layoutInflater)

        database = FirebaseDatabase.getInstance()
        userList = ArrayList()
        database!!.reference.child("User")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (snapshot1 in snapshot.children){
                        val user = snapshot1.getValue(UserDataModel::class.java)
                        if(user!!.uid != FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }
                    binding.chatList.adapter = ChatAdaptor(requireContext(),userList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        return binding.root
    }
}

