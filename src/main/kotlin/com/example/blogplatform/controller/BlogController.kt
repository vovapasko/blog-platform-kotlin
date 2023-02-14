package com.example.blogplatform.controller


import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class BlogController {

    @GetMapping("/")
    fun getBlog(model: Model): String {
        model["title"] = "Blog"
        return "blog"
    }
}