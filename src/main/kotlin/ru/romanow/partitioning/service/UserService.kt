package ru.romanow.partitioning.service

import ru.romanow.partitioning.domain.User

interface UserService {
    fun findAll(): List<User>
    fun findById(id: Int): User
    fun create(login: String): User
    fun delete(id: Int)
}