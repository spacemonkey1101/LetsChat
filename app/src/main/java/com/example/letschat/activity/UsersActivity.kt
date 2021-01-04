package com.example.letschat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letschat.R
import com.example.letschat.adapter.UserAdapter
import com.example.letschat.model.User
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        usersRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        var userList = arrayListOf<User>()
        userList.add(User("Brian","https://pbs.twimg.com/profile_images/966442748990521344/_qX5URB9_400x400.jpg"))
        userList.add(User("John","https://c8.alamy.com/comp/ETTX8J/john-cleese-filming-the-british-comedy-film-monty-python-and-the-holy-ETTX8J.jpg"))
        userList.add(User("Eric","https://upload.wikimedia.org/wikipedia/en/thumb/c/cb/Flyingcircus_2.jpg/250px-Flyingcircus_2.jpg"))
        userList.add(User("Terry","https://static01.nyt.com/images/2020/01/23/obituaries/23jones/merlin_60759704_20e5c09f-c54b-4040-9c9e-9144117552a1-mobileMasterAt3x.jpg"))
        userList.add(User("Graham","https://advancelocal-adapter-image-uploads.s3.amazonaws.com/image.oregonlive.com/home/olive-media/width2048/img/tv/photo/2018/10/10/montycastjpg-7ef393e2355a42aa.jpg"))

        var userAdapter = UserAdapter(this,userList)

        usersRecyclerView.adapter = userAdapter
    }
}