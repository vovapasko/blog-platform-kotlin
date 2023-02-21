package com.example.blogplatform.rest.model

import com.example.blogplatform.models.Post


data class ApiPost (
    val title: String,
    var content: String,
    var author: String,
){
    companion object{
        fun toApiPost(post: Post): ApiPost = ApiPost(
            title = post.title,
            content = post.content,
            author = post.author.login,
        )
    }
}