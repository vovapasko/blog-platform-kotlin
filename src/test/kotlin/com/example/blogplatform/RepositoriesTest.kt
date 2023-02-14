package com.example.blogplatform

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
){
    @Test
    fun `When findByIdOrNull then return Article`(){
        val johnDoe = User("johndoe", "John", "Doe", )
        entityManager.persist(johnDoe)
        val article = Article("title1", "Headline1", "Content1", johnDoe)
        entityManager.persist(article)
        entityManager.flush()
        val foundArticle = articleRepository.findByIdOrNull(article.id!!)
        assertThat(foundArticle).isEqualTo(article)
    }

}