package com.example.blogplatform.models.request

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User

data class ApiPostRequest(
    val title: String,
    val content: String,
    val authorLogin: String
) {
    companion object {
        fun toPost(
            apiPostRequest: ApiPostRequest,
            author: User
        ): Post = Post(
            title = apiPostRequest.title,
            headline = apiPostRequest.title,
            content = apiPostRequest.content,
            author = author
        )
    }
}
