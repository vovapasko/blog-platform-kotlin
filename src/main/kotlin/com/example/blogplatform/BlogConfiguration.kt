package com.example.blogplatform

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {
        val jdoe = userRepository.save(User("jdoe", "John", "Doe"))
        articleRepository.save(
            Article(
                title = "Lorem ipsum",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = jdoe
            )
        )
        articleRepository.save(
            Article(
                title = "Lorem ipsum",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = jdoe
            )
        )
    }

}