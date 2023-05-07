package com.example.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

// 评论数据模型
@Serializable
data class Comment(

    val content: String = "", // 评论内容
    val author: String = "", // 评论作者
    val timestamp: Long = 0, // 评论时间
    val postId :String = "", // 属于哪个帖子
    val zan : Int = 0 ,// 获赞数
    val authorAvatar :String = "", // 评论作者头像

    @BsonId
    val id : String = ObjectId().toString()
)
