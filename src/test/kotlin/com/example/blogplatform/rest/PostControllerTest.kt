package com.example.blogplatform.rest

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User
import com.example.blogplatform.models.request.ApiPostRequest
import com.example.blogplatform.repositories.PostRepository
import com.example.blogplatform.rest.model.ApiPost
import com.example.blogplatform.services.PostService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
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
    private lateinit var userController: UserController

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
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
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
        every { userController.findByLogin(any()) } returns johnDoe
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/article/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockApiPostRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(lorem5Post)))
        verify(exactly = 1) { postService.createPost(any()) }
    }

}