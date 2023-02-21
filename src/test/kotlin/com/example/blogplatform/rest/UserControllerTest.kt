package com.example.blogplatform.rest

import com.example.blogplatform.models.User
import com.example.blogplatform.repositories.UserRepository
import com.example.blogplatform.services.PostService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(UserController::class)
class UserControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userService: PostService

    @MockkBean
    lateinit var userRepository: UserRepository

    @Test
    fun `List users`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        val janeDoe = User("janeDoe", "Jane", "Doe")
        every { userRepository.findAll() } returns listOf(johnDoe, janeDoe)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].login").value(johnDoe.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].login").value(janeDoe.login))
    }
}