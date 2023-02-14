package com.example.blogplatform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogPlatformApplication

fun main(args: Array<String>) {
    runApplication<BlogPlatformApplication>(*args)
}
