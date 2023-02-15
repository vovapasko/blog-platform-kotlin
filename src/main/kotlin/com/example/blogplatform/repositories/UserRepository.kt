package com.example.blogplatform.repositories


import com.example.blogplatform.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}
