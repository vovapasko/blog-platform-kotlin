package com.example.blogplatform.repositories

import com.example.blogplatform.models.Post
import org.springframework.data.repository.CrudRepository


interface PostRepository : CrudRepository<Post, Long> {
    fun findBySlug(slug: String): Post?
    fun findByOrderByAddedAtDesc(): Iterable<Post>
    fun deleteArticleById(id: Long)
}