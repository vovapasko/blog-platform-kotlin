package com.example.blogplatform.rest

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User
import com.example.blogplatform.models.request.ApiPostRequest
import com.example.blogplatform.repositories.PostRepository
import com.example.blogplatform.repositories.UserRepository
import com.example.blogplatform.rest.model.ApiPost
import com.example.blogplatform.services.PostService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@WebMvcTest(PostController::class)
internal class PostControllerTest(
    @Autowired val mockMvc: MockMvc,
) {
    @MockkBean
    private lateinit var postService: PostService

    @MockkBean
    private lateinit var userRepository: UserRepository

    private val mapper = jacksonObjectMapper()

    @MockkBean
    private lateinit var postRepository: PostRepository


    companion object {
        val mockApiPostRequest = ApiPostRequest(
            "Lorem",
            "dolor sit amet",
            "johnDoe"
        )

        val johnDoe = User("johnDoe", "John", "Doe")
        val lorem5Post = Post("Lorem", "Lorem", "dolor sit amet", johnDoe, id = 1)
        val ipsumPost = Post("Ipsum", "Ipsum", "dolor sit amet", johnDoe, id = 2)


    }


    @Test
    fun `List articles`() {
        every { postService.findByOrderByAddedAtDesc() } returns listOf(lorem5Post, ipsumPost)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].author.login").value(johnDoe.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].slug").value(lorem5Post.slug))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].author.login").value(johnDoe.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].slug").value(ipsumPost.slug))
    }

    @Test
    fun `Delete existing post get 201`() {
        every { postService.deleteArticleById(lorem5Post.id!!) } returns true

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/article/${lorem5Post.id}"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `Delete non existing post get 404`() {
        val nonExistingId = 1234
        every { postService.deleteArticleById(nonExistingId.toLong()) } returns false
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/article/${nonExistingId}"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `Create post get 204`() {
        every { postService.createPost(any()) } returns lorem5Post
        every { userRepository.findByLogin(any()) } returns johnDoe
        val expectedPost = ApiPost(
            title = lorem5Post.title,
            content = lorem5Post.content,
            author = johnDoe.login
        )

        mockMvc.perform(
            makePostRequest(mockApiPostRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(expectedPost)))

        verify(exactly = 1) { postService.createPost(any()) }
    }

    @Test
    fun `Create post with non existing user return 403`() {

        every { userRepository.findByLogin(any()) } returns null

        mockMvc.perform(
            makePostRequest(mockApiPostRequest.copy(authorLogin = "doesNotExist"))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
        verify(exactly = 1) { userRepository.findByLogin(any()) }
        verify(exactly = 0) { postService.createPost(any()) }
    }

    @ParameterizedTest
    @CsvSource(
        "'', 'content', 'johnDoe'",
        "'title', '', 'johnDoe'",
        "'title', 'content', ''"
    )
    fun `Create post with empty fields return 403`(
        title: String?,
        content: String?,
        authorLogin: String?
    ) {
        val request = ApiPostRequest(title.orEmpty(), content.orEmpty(), authorLogin.orEmpty())
        mockMvc.perform(
            makePostRequest(request)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }


    private fun makePostRequest(body: ApiPostRequest) = MockMvcRequestBuilders.post("/api/article/")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(body))


}