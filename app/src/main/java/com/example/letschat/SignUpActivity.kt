package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
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

