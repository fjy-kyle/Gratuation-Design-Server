package com.example.data

import com.example.data.model.Post

interface PostDataSource {

    suspend fun getAllPosts(): List<Post>

    suspend fun insertPost(post: Post)

    suspend fun getPostById(id: String): Post?

    suspend fun getPostByTitle(title: String): List<Post?>?

    suspend fun updatePostZan(id: String, isIncrease: Boolean) // 用户点赞后帖子的赞 +1， 取消则赞 -1
}