package com.example.blogplatform.controller

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogControllerTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Asser get blog`(){
        val entity = restTemplate.getForEntity<String>("/blogs")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isEqualTo("Hello world")
    }

    @Test
    fun `Asser create blog`(){
        val entity = restTemplate.postForEntity<String>("/blogs")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(entity.body).isEqualTo("Create blog")
    }


}