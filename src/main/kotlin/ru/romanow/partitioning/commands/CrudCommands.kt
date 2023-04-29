package ru.romanow.partitioning.commands

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import ru.romanow.partitioning.service.UserService

@ShellComponent
class CrudCommands(
    private val userService: UserService,
    private val objectMapper: ObjectMapper,
) {

    @ShellMethod(value = "Create user", key = ["create"])
    fun create(@ShellOption login: String): String =
        objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(userService.create(login))

    @ShellMethod(value = "Find all users", key = ["find-all"])
    fun findAll(): String = objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(userService.findAll())

    @ShellMethod(value = "Find user by Id", key = ["find-by-id"])
    fun findById(@ShellOption id: Int): String =
        objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(userService.findById(id))

    @ShellMethod(value = "Delete user", key = ["delete"])
    fun delete(@ShellOption id: Int) {
        userService.delete(id)
    }
}