package com.example.blogplatform.rest


import com.example.blogplatform.models.request.ApiPostRequest
import com.example.blogplatform.rest.model.ApiPost
import com.example.blogplatform.services.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class PostController(
    private val postService: PostService,
    private val userController: UserController
) {

    @GetMapping("/")
    fun findAll() = postService.findByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) = postService.findBySlug(slug) ?: throw ResponseStatusException(
        HttpStatus.NOT_FOUND, "This article does not exist"
    )

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteOne(@PathVariable id: String) =
        if (postService.deleteArticleById(id.toLong())) Unit else throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "This article does not exist"
        )

    @PostMapping("/", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createPost(
        @RequestBody apiPostRequest: ApiPostRequest
    ): ApiPost {
        val user = userController.findByLogin(apiPostRequest.authorLogin)
        val post = ApiPostRequest.toPost(apiPostRequest, user)
        val createdPost = postService.createPost(post)
        return ApiPost.toApiPost(createdPost)
    }

}