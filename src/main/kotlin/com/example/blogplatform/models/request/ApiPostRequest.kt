package com.example.blogplatform.models.request

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User
import jakarta.validation.constraints.NotNull


data class ApiPostRequest(
    @NotNull
    val title: String,
    @NotNull
    val content: String,
    @NotNull
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
