package com.example.data.model


import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.StructureKind
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Post (

    val title: String  = "",
    val content :String = "",
    val author: String = "",
    val timestamp: Long = 0,
    val zan: Int = 0,
    val commentCount: Int = 0,
    val authorAvatar :String = "",

    @BsonId
    val id : String = ObjectId().toString()

)