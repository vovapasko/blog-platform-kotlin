package com.example.blogplatform.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/blogs"])
class BlogController {

    @RequestMapping(method = [RequestMethod.GET])
    fun getBlog() = "Hello world"

    @RequestMapping(method = [RequestMethod.POST])
    fun createBlog() = "Create blog"

    @RequestMapping(method = [RequestMethod.PUT])
    fun updateBlog() = "Update blog"

    @RequestMapping(method = [RequestMethod.DELETE])
    fun deleteBlog() = "Delete blog"
}