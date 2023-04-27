package ru.romanow.partitioning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PartitioningApplication

fun main(args: Array<String>) {
    runApplication<PartitioningApplication>(*args)
}