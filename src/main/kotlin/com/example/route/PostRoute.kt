package com.example.route

import com.example.controller.postController.PostController
import com.example.data.model.Post
import com.example.util.BaseModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


fun Route.postRoute(
    postController: PostController
){
    val baseUrl = "http://47.108.253.91:8080"

    //用户发帖，存入数据库
    post("/posts/insert") {
        val title = call.parameters["title"]
        val content = call.receiveText()
        val author = call.parameters["author"]
        val post = Post(
            title = title!!,
            content = content,
            author = author!!,
            timestamp = System.currentTimeMillis(),
            authorAvatar = "$baseUrl/avatar/${author}.png"
        )

        postController.insertPost(post) // 添加帖子
        val tryFind = postController.getPostById(post.id) // 查找帖子
        val result = BaseModel( data = tryFind)
        call.respond(
            HttpStatusCode.OK,
            result
        )
    }

    // 获取所有帖子
    get("/posts") {
        call.respond(
            HttpStatusCode.OK,
            postController.getAllPosts()
        )
    }

    // 根据id查找相应帖子
    get("/posts/findById") {
        val postId = call.parameters["id"]
        val post  = postController.getPostById(postId!!)
        val result = if (post!=null) {
            BaseModel( data = post )
        } else {
            BaseModel(
                errorCode = 1,
                errorMsg = "找不到指定帖子"
            )
        }
        call.respond(
            HttpStatusCode.OK,
            result
        )
    }

    // 根据title查找相应帖子
    get("/posts/findByTitle") {
        val title = call.parameters["title"]
        val posts  = postController.getPostByTitle(title!!)
        val result = if (posts!=null) {
            BaseModel( data = posts )
        } else {
            BaseModel(
                errorCode = 1,
                errorMsg = "无法根据id找到指定帖子"
            )
        }
        call.respond(
            HttpStatusCode.OK,
            result
        )
    }

    // 修改帖子的点赞数
    put("/posts/updateZan") {
        val id = call.parameters["id"]!!
        val flag = call.parameters["flag"]!!
        val isIncrease = flag == "1" //如果参数值是1，则表示增加点赞数，反则减少点赞
        postController.updatePostZan(id, isIncrease)
        call.respond(
            HttpStatusCode.OK,
            "update success"
        )
    }

}