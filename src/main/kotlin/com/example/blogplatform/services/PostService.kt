package com.example.blogplatform.services

import com.example.blogplatform.models.Post
import com.example.blogplatform.repositories.PostRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostService(private val repository: PostRepository) {

    fun findBySlug(slug: String) = repository.findBySlug(slug)

    fun findByOrderByAddedAtDesc() = repository.findByOrderByAddedAtDesc()

    @Transactional
    fun deleteArticleById(articleId: Long) = if (repository.existsById(articleId)) {
        repository.deleteArticleById(articleId)
        true
    } else {
        false
    }

    @Transactional
    fun createPost(post: Post) = repository.save(post)

}