package com.example.letschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.model.User

class UserAdapter(private val context:Context, private val userList:ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.textUserName.text = user.userName
        Glide.with(context).load(user.userImage).placeholder(R.mipmap.ic_launcher_round).into(holder.userImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val textUserName = view.findViewById<TextView>(R.id.userName)
        val textTemp = view.findViewById<TextView>(R.id.temp)
        val userImage = view.findViewById<ImageView>(R.id.user_image)

    }
}