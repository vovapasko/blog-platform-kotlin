package com.example.blogplatform.services

import com.example.blogplatform.repositories.ArticleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticleService(private val repository: ArticleRepository) {

    fun findBySlug(slug: String) = repository.findBySlug(slug)

    fun findByOrderByAddedAtDesc() = repository.findByOrderByAddedAtDesc()

    @Transactional
    fun deleteArticleById(articleId: Long) = if (repository.existsById(articleId)) {
        repository.deleteArticleById(articleId)
        true
    } else {
        false
    }


}