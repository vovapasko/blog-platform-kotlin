package com.example.blogplatform.rest


import com.example.blogplatform.repositories.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {

    @GetMapping("/")
    fun findAll() = repository.findByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) = repository.findBySlug(slug) ?: throw ResponseStatusException(
        HttpStatus.NOT_FOUND, "This article does not exist"
    )

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: String) = repository.deleteArticleById(id.toLong())

}