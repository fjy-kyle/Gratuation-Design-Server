package com.example.data.model


import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(

    val username :String = "",
    val password: String = "",
    val nickname: String? = null, // 昵称
    val avatar: String? = null, // 头像链接
    val zanCount: Int = 0, // 获赞总数
    val postCount: Int = 0, // 发帖数
    val commentCount: Int = 0, // 评论数
    val sign: String = "", // 个性签名

    @BsonId
    val id: String = ObjectId().toString()
)
