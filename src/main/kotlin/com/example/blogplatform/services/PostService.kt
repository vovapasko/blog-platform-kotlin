package com.example.blogplatform.services

import com.example.blogplatform.models.Post
import com.example.blogplatform.repositories.PostRepository
import jakarta.persistence.EntityNotFoundException
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

    @Transactional
    fun updatePost(id: String, post: Post): Post {
        val postToUpdate = repository.findById(id.toLong()).orElse(null)
        postToUpdate?.let {
            it.content = post.content
            it.title = post.title
            it.headline = post.headline
            it.slug = post.slug
            repository.save(it)
        }
        return postToUpdate ?: throw EntityNotFoundException("Post not found")
    }

}