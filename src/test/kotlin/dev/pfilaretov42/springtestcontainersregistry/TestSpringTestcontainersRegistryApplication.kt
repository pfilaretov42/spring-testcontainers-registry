package dev.pfilaretov42.springtestcontainersregistry

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SpringTestcontainersRegistryApplication>().with(TestcontainersConfiguration::class).run(*args)
}
