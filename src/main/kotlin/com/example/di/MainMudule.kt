package com.example.di

import com.example.controller.commentController.CommentController
import com.example.controller.postController.PostController
import com.example.controller.roomController.RoomController
import com.example.controller.userController.UserController
import com.example.data.*
import com.example.data.MessageDataSource
import com.example.data.MessageDataSourceImpl
import com.example.data.PostDataSource
import com.example.data.PostDataSourceImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("social_app_db")
    }
    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
    single {
        UserController(get())
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }

    single {
        RoomController(get())
    }
    single<PostDataSource> {
        PostDataSourceImpl(get())
    }
    single {
        PostController(get())
    }
    single<CommentDataSource> {
        CommentDataSourceImpl(get())
    }
    single {
        CommentController(get())
    }

}