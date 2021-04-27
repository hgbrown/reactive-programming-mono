package dev.hbrown.demo

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import java.lang.RuntimeException

class MonoExamples {

    @Test
    internal fun `creating a Mono using just`() {
        Mono.just("A")
            .log()
            .subscribe()
    }

    @Test
    fun `using the value from the consumer`() {
        Mono.just("A")
            .log()
            .subscribe { println(it) }
    }

    @Test
    fun `running code on completion`() {
        Mono.just("A")
            .log()
            .subscribe(
                { println(it) },
                null,
                { println("Done!") }
            )
    }

    @Test
    internal fun `creating a Mono that does not produce a value`() {
        Mono.empty<Unit>()
            .log()
            .subscribe()
    }

    @Test
    internal fun `using an empty mono`() {
        val result = if(System.currentTimeMillis() % 2 == 0L) {
            Mono.just("A")
        } else {
            Mono.empty()
        }

        result
            .log()
            .subscribe()
    }

    @Test
    fun `using defaultIfEmpty on an empty Mono`() {
        Mono.empty<String>()
            .defaultIfEmpty("B")
            .log()
            .subscribe { println("Got: $it") }
    }

    @Test
    fun `creating a Mono that produces an error`() {
        Mono.error<RuntimeException>(RuntimeException("runtime error"))
            .log()
            .subscribe(
                null
            ) { println("** ERROR: ${it.message}") }
    }

    @Test
    fun `creating a Mono that produces a checked exception`() {
        Mono.error<Exception>(Exception("checked exception"))
            .log()
            .subscribe(
                null
            ) { println("** EXCEPTION: ${it.message}") }
    }

    @Test
    fun `resuming after error with alternate Mono`() {
        Mono.error<String>(Exception("error"))
            .onErrorResume {
                Mono.just("B")
            }
            .log()
            .subscribe({
                println("Next: $it")
            }) {
                println("Error: $it")
            }
    }

    @Test
    fun `resuming after error with alternate value`() {
        Mono.error<String>(Exception("error"))
            .onErrorReturn("B")
            .log()
            .subscribe()
    }

}
