package ru.romanow.partitioning.commands

import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import ru.romanow.partitioning.service.UserService

@ShellComponent
class CrudCommands(
    private val userService: UserService,
) {

    @ShellMethod(value = "Create user", key = ["create"])
    fun create(@ShellOption login: String) = userService.create(login)

    @ShellMethod(value = "Find all users", key = ["find-all"])
    fun findAll() = userService.findAll()

    @ShellMethod(value = "Find user by Id", key = ["find-by-id"])
    fun findById(@ShellOption id: Int) = userService.findById(id)

    @ShellMethod(value = "Delete user", key = ["delete"])
    fun delete(@ShellOption id: Int) {
        userService.delete(id)
    }
}