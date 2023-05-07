package com.example.controller.userController

import com.example.data.UserDataSource
import com.example.data.model.User
import java.beans.beancontext.BeanContextServiceAvailableEvent

class UserController(
    private val userDataSource: UserDataSource
) {

    suspend fun getUserByUsername(username: String): User? {
        return userDataSource.getUserByUsername(username)
    }

    suspend fun insertUser(user: User) {
        userDataSource.insertUser(user)
    }

    suspend fun updateUserAvatar(username:String, avatar: String){
        userDataSource.updateUserAvatar(username,avatar)
    }

    suspend fun updateSignByName(name:String, sign: String) {
        userDataSource.updateSignByName(name, sign)
    }
}