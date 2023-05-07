package com.example.data

import com.example.data.model.Comment
import com.example.data.model.Post

interface CommentDataSource {


    // 根据帖子id返回评论列表
    suspend fun getCommentByPostId(postId: String): List<Comment>?

    // 插入评论，把帖子id和评论关联起来
    suspend fun insertComment(comment: Comment)

    // 评论可被点赞
    suspend fun updateCommentZan(commentId: String, isIncrease: Boolean) // 用户点赞后帖子的赞 +1， 取消则赞 -1

    // 根据id查找对应评论
    suspend fun getCommentByCommentId(commentId: String): Comment?
}