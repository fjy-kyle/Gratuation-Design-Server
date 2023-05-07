package com.example.controller.commentController

import com.example.data.CommentDataSource
import com.example.data.model.Comment

class CommentController(
    private val commentDataService: CommentDataSource
) {

    suspend fun getCommentByPostId(postId: String): List<Comment>? {
        return commentDataService.getCommentByPostId(postId)
    }

    suspend fun insertComment(comment: Comment){
        commentDataService.insertComment(comment)
    }

    suspend fun updateCommentZan(commentId: String, isIncrease: Boolean){
        commentDataService.updateCommentZan(commentId, isIncrease)
    }

    suspend fun getCommentByCommentId(commentId: String): Comment? {
        return commentDataService.getCommentByCommentId(commentId)
    }
}