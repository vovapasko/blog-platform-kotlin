package com.example.blogplatform.rest


import com.example.blogplatform.models.request.ApiPostRequest
import com.example.blogplatform.repositories.UserRepository
import com.example.blogplatform.rest.model.ApiPost
import com.example.blogplatform.services.PostService
import jakarta.validation.Valid
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
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class PostController(
    private val postService: PostService,
    private val userRepository: UserRepository
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
        @RequestBody @Valid apiPostRequest: ApiPostRequest
    ): ApiPost {
        val user = userRepository.findByLogin(apiPostRequest.authorLogin)
        val post = user?.let { ApiPostRequest.toPost(apiPostRequest, it) }
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "User ${apiPostRequest.authorLogin} does not exist"
            )
        val createdPost = postService.createPost(post)
        return ApiPost.toApiPost(createdPost)
    }

}