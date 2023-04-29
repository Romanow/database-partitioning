package ru.romanow.partitioning.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.romanow.partitioning.domain.User

interface UserRepository : JpaRepository<User, Int>