package com.example.letschat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.imgProfile

class ChatActivity : AppCompatActivity() {

    private var firebaseUser: FirebaseUser? = null
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent = intent
        val userId = intent.getStringExtra("userId")
        firebaseUser = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(userId!!)

        imgBack.setOnClickListener{
            onBackPressed()
        }

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                chat_user_name.text = user?.userName
                if (user?.profileImage.equals("")) {
                    imgProfile.setImageResource(R.mipmap.ic_launcher_round)
                } else {
                    Glide.with(this@ChatActivity).load(user?.profileImage)
                        .placeholder(R.mipmap.ic_launcher_round).into(imgProfile)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        btn_send_message.setOnClickListener {
            var message:String = edit_text_message.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(applicationContext , "Enter text to send" , Toast.LENGTH_SHORT).show()
            } else {
                sendMessgae(firebaseUser?.uid!!, userId , message)
            }

        }
    }

    private fun sendMessgae(senderId: String,receiverId:String,message:String) {
        var databaseReference: DatabaseReference? =
            FirebaseDatabase.getInstance()
            .getReference()

        var hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId",receiverId)
        hashMap.put("message",message)

        //sending the value to realtime database
        databaseReference?.child("chat")?.push()?.setValue(hashMap)


    }
}