package com.example.controller.postController

import com.example.data.PostDataSource
import com.example.data.model.Post

class PostController(
    private val postDataSource: PostDataSource
) {
    suspend fun getAllPosts(): List<Post> {
        return postDataSource.getAllPosts()
    }

    suspend fun insertPost(post: Post) {
        postDataSource.insertPost(post)
    }

    suspend fun getPostById(id: String) : Post?{
        return postDataSource.getPostById(id)
    }

    suspend fun getPostByTitle(title: String): List<Post?>? {
        return postDataSource.getPostByTitle(title)
    }

    suspend fun updatePostZan(id: String, flag: Boolean) {
        return postDataSource.updatePostZan(id, flag)
    }
}