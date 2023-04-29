package ru.romanow.partitioning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import ru.romanow.camunda.config.DatabaseTestConfiguration
import ru.romanow.partitioning.domain.User
import ru.romanow.partitioning.repositories.UserRepository

@SpringBootTest
@Import(DatabaseTestConfiguration::class)
class PartitioningApplicationTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun test() {
        var users = listOf(User(login = "alex"), User(login = "max"))
        userRepository.saveAllAndFlush(users)

        users = userRepository.findAll()
        assertThat(users).hasSize(2)
    }
}