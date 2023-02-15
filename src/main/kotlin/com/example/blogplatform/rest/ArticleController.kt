package com.example.blogplatform.rest


import com.example.blogplatform.services.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(private val service: ArticleService) {

    @GetMapping("/")
    fun findAll() = service.findByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) = service.findBySlug(slug) ?: throw ResponseStatusException(
        HttpStatus.NOT_FOUND, "This article does not exist"
    )

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: String) =
        if (service.deleteArticleById(id.toLong())) Unit else throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "This article does not exist"
        )

}