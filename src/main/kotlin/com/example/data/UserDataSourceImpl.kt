package com.example.data

import com.example.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.litote.kmongo.setValue

class UserDataSourceImpl(
    private val db: CoroutineDatabase
): UserDataSource {
    private val users = db.getCollection<User>()

    override suspend fun getUserByUsername(username: String): User? {
        return users.findOne(User::username eq username)
    }

    override suspend fun insertUser(user: User) {
        users.insertOne(user)
    }

    override suspend fun updateUserAvatar(username: String, avatar: String) {
         users.updateOne(User::username eq username, setValue(User::avatar, avatar) )
    }

    override suspend fun updateSignByName(name: String, sign:String) {
        users.updateOne(User::username eq name , setValue(User::sign, sign))
    }
}