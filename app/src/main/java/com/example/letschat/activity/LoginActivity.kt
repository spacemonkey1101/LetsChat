package com.example.letschat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.letschat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        //if user is logged in then navigate to user screen
        if(firebaseUser != null) {
            val intent = Intent(this@LoginActivity, UsersActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_log_in.setOnClickListener {
            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext,"email is required", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext,"password is required", Toast.LENGTH_SHORT).show()
            }
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        //set entry to empty strings
                        edit_text_email.setText("")
                        edit_text_password.setText("")
                        //open next activity
                        val intent = Intent(this@LoginActivity, UsersActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"email and password are not valid!", Toast.LENGTH_SHORT).show()
                    }
                }


        }
        btn_sign_up.setOnClickListener {
            //open next activity
            val intent1 = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent1)
            finish()
        }
    }
}