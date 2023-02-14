package com.example.blogplatform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogPlatformApplication

fun main(args: Array<String>) {
    runApplication<BlogPlatformApplication>(*args)
}
