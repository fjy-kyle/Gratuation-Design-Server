package com.example.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
data class Message (

    val text: String = "",
    val username: String = "",
    val timestamp: Long = 0,
    val senderAvatar: String = "",

    @BsonId
    val id: String = ObjectId().toString()
)