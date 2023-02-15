package com.example.blogplatform.rest

import com.example.blogplatform.models.Article
import com.example.blogplatform.models.User
import com.example.blogplatform.repositories.ArticleRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
class ArticleControllerTest(@Autowired val mockMvc: MockMvc) {


    @MockkBean
    lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        val lorem5Article = Article("Lorem", "Lorem", "dolor sit amet", johnDoe)
        val ipsumArticle = Article("Ipsum", "Ipsum", "dolor sit amet", johnDoe)

        every { articleRepository.findByOrderByAddedAtDesc() } returns listOf(lorem5Article, ipsumArticle)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].author.login").value(johnDoe.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].slug").value(lorem5Article.slug))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].author.login").value(johnDoe.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].slug").value(ipsumArticle.slug))
    }

}