package com.example.blogplatform.models.request

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User
import jakarta.validation.constraints.NotBlank


data class ApiPostRequest(
    @field:NotBlank(message = "title must be set")
    val title: String,
    @field:NotBlank(message = "content must be set")
    val content: String,
    @field:NotBlank(message = "authorLogin must be set")
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
