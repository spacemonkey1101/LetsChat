package com.example.letschat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letschat.R
import com.example.letschat.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.activity_users.imgBack

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgBack.setOnClickListener{
            onBackPressed()
        }

        imgProfile.setOnClickListener {
            val intent1 = Intent(this@ProfileActivity, UsersActivity::class.java)
            startActivity(intent1)
            finish()
        }
    }
}