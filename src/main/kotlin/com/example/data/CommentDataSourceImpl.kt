package com.example.data

import com.example.data.model.Comment
import com.example.data.model.Post
import com.example.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class CommentDataSourceImpl(
    private val db: CoroutineDatabase
) : CommentDataSource{

    private val comments = db.getCollection<Comment>()
    private val users = db.getCollection<User>()
    private val posts = db.getCollection<Post>()

    override suspend fun getCommentByPostId(postId: String): List<Comment> {
        return comments.find(Comment::postId eq postId)
            .descendingSort(Comment::timestamp)
            .toList()
    }

    override suspend fun insertComment(comment: Comment){
        // 找到评论作者
        val user = users.findOne(User::username eq comment.author)
        // 作者的评论数 + 1
        users.updateOne(User::username eq comment.author, setValue(User::commentCount, user!!.commentCount + 1))
        // 找到对应帖子
        val post = posts.findOne(Post::id eq comment.postId)
        // 帖子的评论数 + 1
        posts.updateOne(Post::id eq post!!.id, setValue(Post::commentCount, post.commentCount + 1) )
        comments.insertOne(comment)
    }

    override suspend fun updateCommentZan(commentId: String, isIncrease: Boolean) {
        val comment = comments.findOneById(commentId)!! //先找出对应评论
        // 更新评论的获赞数
        if (isIncrease) {
           comments.updateOneById(commentId, setValue(Comment::zan, comment.zan+1))
        } else {
            comments.updateOneById(commentId, setValue(Comment::zan, comment.zan-1))
        }
        // 更新评论作者的获赞数
        val author = comment.author // 找到对应作者名
        val user = users.findOne(User::username eq author) //根据作者名拿到user
        if (isIncrease) {
            users.updateOne(User::username eq author, setValue(User::zanCount,user!!.zanCount + 1))
        } else {
            users.updateOne(User::username eq author, setValue(User::zanCount,user!!.zanCount - 1))
        }
    }

    override suspend fun getCommentByCommentId(commentId: String): Comment? {
        return comments.findOneById(commentId)
    }
}