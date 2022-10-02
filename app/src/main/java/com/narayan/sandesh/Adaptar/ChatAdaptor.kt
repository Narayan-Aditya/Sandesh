package com.narayan.sandesh.Adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narayan.sandesh.ChatActivity
import com.narayan.sandesh.R
import com.narayan.sandesh.UserDataModel
import com.narayan.sandesh.databinding.ChatsectionBinding
import kotlinx.android.synthetic.main.activity_signup.view.*

class ChatAdaptor(var context : Context, var list : ArrayList<UserDataModel>): RecyclerView.Adapter<ChatAdaptor.ChatViewHolder>() {

    inner class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding : ChatsectionBinding = ChatsectionBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.chatsection,parent,false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = list[position]
        holder.binding.ChatUserName.text = user.userName
        holder.itemView.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("uid",user.uid)
            context.startActivities(arrayOf(intent))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


