package com.example.letschat.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.letschat.R
import com.example.letschat.model.User
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.activity_users.imgBack
import java.io.IOException
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private var filePath: Uri? = null
    private lateinit var storage:FirebaseStorage
    private lateinit var storageRef:StorageReference


    private final val PICK_IMAGE_REQUEST:Int = 2020


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(firebaseUser.uid)

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference



            databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    userName.setText(user?.userName)
                    if (user?.profileImage.equals("")) {
                        user_image.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide.with(this@ProfileActivity).load(user?.profileImage)
                            .placeholder(R.mipmap.ic_launcher_round).into(user_image)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                }

            })

        imgBack.setOnClickListener{
            onBackPressed()
        }

        user_image.setOnClickListener {
            chooseImage()
        }

        btn_save.setOnClickListener {
            uploadImage()
            progressBar.visibility = View.VISIBLE
        }
    }
    private fun chooseImage() {
        val intent:Intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode != null) {
            filePath = data?.data
            try {

                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                user_image.setImageBitmap(bitmap)
                btn_save.visibility = View.VISIBLE
            } catch (e:IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if(filePath != null) {
            val ref:StorageReference = storageRef.child("image/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!).addOnSuccessListener {
                val hashMap: HashMap<String, String> = HashMap()
                hashMap.put("userName", userName.text.toString())
                hashMap.put("profileImage", filePath.toString())

                databaseReference.updateChildren(hashMap as Map<String, Any>)

                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
                    btn_save.visibility = View.GONE


            }
                .addOnFailureListener{
                        progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Failed " + it.message, Toast.LENGTH_SHORT).show()

                }


        }
    }
}