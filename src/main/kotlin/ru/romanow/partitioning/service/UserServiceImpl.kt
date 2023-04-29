package ru.romanow.partitioning.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.partitioning.domain.User
import ru.romanow.partitioning.repositories.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    @Transactional(readOnly = true)
    override fun findAll(): List<User> = userRepository.findAll()

    @Transactional(readOnly = true)
    override fun findById(id: Int): User = userRepository.findById(id).get()

    @Transactional
    override fun create(login: String): User = userRepository.save(User(login = login))

    @Transactional
    override fun delete(id: Int) {
        userRepository.deleteById(id)
    }
}