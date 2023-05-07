package com.example.route

import com.example.controller.commentController.CommentController
import com.example.controller.postController.PostController
import com.example.data.model.Comment
import com.example.util.BaseModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commentRoute(
    commentController: CommentController
){
    val baseUrl = "http://47.108.253.91:8080"

    // 根据帖子id返回评论列表
    get("/comments"){
        val postId = call.parameters["id"]!!
        val tryFind  = commentController.getCommentByPostId(postId)
        val result =if (tryFind!!.isEmpty()) {
            emptyList()
        } else {
             tryFind
        }
        call.respond(
            HttpStatusCode.OK,
            result
        )
    }

    // 插入评论，把帖子id和评论关联起来
    post("/comments/insert"){
        val postId =  call.parameters["id"]!!
        val author = call.parameters["author"]!!
        val content = call.parameters["content"]!!
        val comment = Comment(
            postId  = postId,
            author = author,
            content = content,
            authorAvatar = "$baseUrl/avatar/${author}.png",
            timestamp = System.currentTimeMillis()
        )
        val id = comment.id
        commentController.insertComment(comment)
        val result = commentController.getCommentByCommentId(id)
        call.respond(
            HttpStatusCode.OK,
            result!!
        )
    }

    // 评论可被点赞
    put("/comments/updateZan") {
        val id = call.parameters["id"]!!
        val flag = call.parameters["flag"]!!
        val isIncrease = flag == "1" //如果参数值是1，则表示增加点赞数，反则减少点赞
        commentController.updateCommentZan(commentId = id, isIncrease = isIncrease)
        call.respond(
            HttpStatusCode.OK,
            "update success"
        )
    }

    // 根据id查找对应评论
    get("/comments/findCommentById"){
        val id = call.parameters["id"]!!
        val tryFind = commentController.getCommentByCommentId(id)
        val result = if (tryFind == null) {
            BaseModel(
                errorCode = 1,
                errorMsg = "无法根据id找到指定评论",
            )
        } else {
            BaseModel(
                data = tryFind
            )
        }
        call.respond(
            HttpStatusCode.OK,
            result
        )
    }
}