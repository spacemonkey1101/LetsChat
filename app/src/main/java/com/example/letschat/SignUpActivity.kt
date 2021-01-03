package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
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
            var userName = edit_text_name.text.toString()
            var email = edit_text_email.text.toString()
            var password = edit_text_password.toString()
            var confirmpassword = edit_text_confirm_password.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(applicationContext,"username is required",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext,"email is required",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext,"password is required",Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(confirmpassword)) {
                Toast.makeText(applicationContext,"please confirm your password",Toast.LENGTH_SHORT).show()
            }
            if(! password.equals(confirmpassword)) {
                Toast.makeText(applicationContext,"passwords do not match",Toast.LENGTH_SHORT).show()
            }
            registerUser(userName,email,password)
        }
    }
    //register user function
    private fun registerUser(userName:String,email:String , password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                //if task is successful add user in database
                if (it.isSuccessful) {
                    var user: FirebaseUser? = auth.currentUser
                    var userId: String? = user?.uid

                    databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Users").child(userId.toString())

                    var hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userId",userId.toString())
                    hashMap.put("userName",userName)
                    hashMap.put("profileImage","")

                    databaseReference.setValue(hashMap).addOnCanceledListener(this){
                        if (it.isSuccessful){
                            //open next activity
                            var intent = Intent(this@SignUpActivity,HomeActivity::class.java)
                            startActivity(intent)
                        }

                    }

                }
            }
    }
}

