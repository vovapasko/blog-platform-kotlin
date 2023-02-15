package com.example.blogplatform.repositories

import com.example.blogplatform.models.Article
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface ArticleRepository : CrudRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findByOrderByAddedAtDesc(): Iterable<Article>
    fun deleteArticleById(id: Long)
}