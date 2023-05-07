package com.example.controller.roomController

import io.ktor.websocket.*

//消息发送者
data class Member(
    val username: String,
    val sessionId: String,
    val socket: WebSocketSession
)