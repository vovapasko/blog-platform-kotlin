package com.example.blogplatform.rest.model

import com.example.blogplatform.models.Post


data class ApiPost (
    val title: String,
    var content: String,
    var author: String,
    var addedAt: String
){
    companion object{
        fun toApiPost(post: Post): ApiPost = ApiPost(
            title = post.title,
            content = post.content,
            author = post.author.login,
            addedAt = post.addedAt.toString()
        )
    }
}