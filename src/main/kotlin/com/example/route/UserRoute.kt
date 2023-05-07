package com.example.route

import com.example.controller.userController.UserController
import com.example.data.model.User
import com.example.util.BaseModel
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.juli.logging.Log
import java.io.File
import java.util.Base64

fun Route.userRoute(
    userController: UserController
) {

    val baseUrl = "http://47.108.253.91:8080"

    post("/user/register") {

        val username = call.parameters["username"]
        val password = call.parameters["password"]
        val nickname = call.parameters["nickname"]
        val avatarBytes = call.receive<ByteArray>() //头像字节数组
        File("/home/uploads/avatar/${username}.png").writeBytes(avatarBytes) // 把头像存进目标文件中
        val user = User(
            username = username!!,
            password = password!!,
            nickname = nickname!!,
            avatar = "$baseUrl/avatar/${username}.png"
        )
        val tryFind = userController.getUserByUsername(username)
        val result = if (tryFind == null) {
            userController.insertUser(user)
            BaseModel(data = user)
        } else BaseModel(
            errorCode = 1,
            errorMsg = "用户名已存在"
        )
        call.respond(HttpStatusCode.OK, result)
    }


    get("/user/login") {
        val username = call.parameters["username"]!!
        val password = call.parameters["password"]!!
        val user = User(username = username, password = password)
        val tryFind = userController.getUserByUsername(user.username)
        val result = if (tryFind == null) {
            BaseModel(
                errorCode = 1,
                errorMsg = "用户名不存在"
            )
        } else if (tryFind.password == user.password) {
            BaseModel(data = tryFind)
        } else {
            BaseModel(
                errorCode = 1,
                errorMsg = "用户名或密码错误"
            )
        }
        call.respond(HttpStatusCode.OK, result)
    }

    // 根据用户名获取用户信息
    get("/user/findByName"){
        val username = call.parameters["username"]!!
        call.respond(
            HttpStatusCode.OK,
            userController.getUserByUsername(username)!!
        )
    }

    // 根据用户名修改sign
    post("/user/updateSign"){
        val name = call.parameters["username"]!!
        val sign = call.parameters["sign"]!!
        userController.updateSignByName(name, sign)
        call.respondText("update success")
    }

    // 可用于更改头像，待用。。。
    put("/bytes") {
        val bytes = call.receive<ByteArray>()
        val username = call.parameters["username"]!!
        File("uploads/avatar_${username}.png").writeBytes(bytes)
        userController.updateUserAvatar(username,"uploads/avatar_${username}.png")
        call.respond("ok ")
    }
}
