package com.example.letschat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.letschat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            val userName = edit_text_name.text.toString()
            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()
            val confirmpassword = edit_text_confirm_password.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(applicationContext,"username is required",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext,"email is required",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext,"password is required",Toast.LENGTH_SHORT).show()
            }
            if (password.length < 6) {
                Toast.makeText(applicationContext,"password should be 6 characters at least",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(confirmpassword)) {
                Toast.makeText(applicationContext,"please confirm your password",Toast.LENGTH_SHORT).show()
            }
            if(! password.equals(confirmpassword)) {
                Toast.makeText(applicationContext,"passwords do not match",Toast.LENGTH_SHORT).show()
            }
            registerUser(userName,email,password)

        }

        btn_log_in.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //register user function
    private fun registerUser(userName:String,email:String , password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                //if task is successful add user in database

                if (it.isSuccessful) {
                    Log.i("FIRE", it.isSuccessful.toString())

                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("profileImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            edit_text_name.setText("")
                            edit_text_email.setText("")
                            edit_text_password.setText("")
                            edit_text_confirm_password.setText("")
                            //open next activity
                            val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                }
            }
    }
}

