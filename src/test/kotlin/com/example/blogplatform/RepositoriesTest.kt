package com.example.blogplatform

import com.example.blogplatform.models.Post
import com.example.blogplatform.models.User
import com.example.blogplatform.repositories.PostRepository
import com.example.blogplatform.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val postRepository: PostRepository
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        entityManager.persist(johnDoe)
        val post = Post("Lorem", "Lorem", "dolor sit amet", johnDoe)
        entityManager.persist(post)
        entityManager.flush()
        val found = postRepository.findByIdOrNull(post.id!!)
        assertThat(found).isEqualTo(post)
    }

    @Test
    fun `When findByLogin then return User`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        entityManager.persist(johnDoe)
        entityManager.flush()
        val user = userRepository.findByLogin(johnDoe.login)
        assertThat(user).isEqualTo(johnDoe)
    }
}