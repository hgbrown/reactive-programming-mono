package dev.hbrown.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveProgrammingMonoApplication

fun main(args: Array<String>) {
    runApplication<ReactiveProgrammingMonoApplication>(*args)
}
