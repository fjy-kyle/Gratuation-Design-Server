package com.example.plugins

import com.example.controller.commentController.CommentController
import com.example.controller.postController.PostController
import com.example.controller.roomController.RoomController
import com.example.controller.userController.UserController
import com.example.route.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*


import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {

    val userController: UserController by inject(UserController::class.java)
    val roomController : RoomController by inject(RoomController::class.java)
    val postController: PostController by inject(PostController::class.java)
    val commentController: CommentController by inject(CommentController::class.java)

    install(Routing) {
        userRoute(userController)
        chatSocket(roomController)
        getAllMessage(roomController)
        postRoute(postController)
        commentRoute(commentController)
        get("/") {
            call.respondText { "hello world." }
        }
        post("/largeText"){
            val text = call.receiveText()
            call.respond(
                HttpStatusCode.OK,
                text
            )
        }
    }
}
