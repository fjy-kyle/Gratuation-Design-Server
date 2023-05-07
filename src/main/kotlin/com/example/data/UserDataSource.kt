package com.example.data

import com.example.data.model.User

interface UserDataSource  {

    suspend fun getUserByUsername(username: String) : User?

    suspend fun insertUser(user: User)

    suspend fun updateSignByName(name:String, sign: String)

    suspend fun updateUserAvatar(username: String, avatar:String)
}