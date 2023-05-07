package com.example.data

import com.example.data.model.Post
import com.example.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.match
import org.litote.kmongo.regex
import org.litote.kmongo.setValue

class PostDataSourceImpl(
    private val db: CoroutineDatabase
): PostDataSource {

    private val posts = db.getCollection<Post>()
    private val users = db.getCollection<User>()

    override suspend fun getAllPosts(): List<Post> {
        return posts.find().toList()
    }

    override suspend fun insertPost(post: Post) {
        posts.insertOne(post) // 存入数据库
        val author = post.author // 找到对应作者名
        val user = users.findOne(User::username eq author) //根据作者名拿到user
        users.updateOne(User::username eq post.author, setValue(User::postCount, user!!.postCount +1))
    }

    override suspend fun getPostById(id: String) : Post? {
        return posts.findOneById(id)
    }

    override suspend fun updatePostZan(id: String,isIncrease : Boolean) {
        val post = posts.findOneById(id) //先找出对应帖子
        // 更新帖子的获赞数
        if (isIncrease) {
            posts.updateOneById(id, setValue(Post::zan, post!!.zan+1))
        } else {
            posts.updateOneById(id, setValue(Post::zan, post!!.zan-1))
        }
        // 更新帖子作者的获赞数
        val author = post.author // 找到对应作者名
        val user = users.findOne(User::username eq author) //根据作者名拿到user
        if (isIncrease) {
            users.updateOne(User::username eq author, setValue(User::zanCount,user!!.zanCount + 1))
        } else {
            posts.updateOne(User::username eq author, setValue(User::zanCount,user!!.zanCount - 1))
        }
    }

    override suspend fun getPostByTitle(title: String): List<Post?> {
        return posts.find(Post::title regex title).toList()
    }
}