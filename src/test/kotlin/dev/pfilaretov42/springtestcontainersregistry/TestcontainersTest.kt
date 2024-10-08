package dev.pfilaretov42.springtestcontainersregistry

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName


@SpringBootTest
class TestcontainersTest {

    companion object {
        private val registry = GenericContainer(DockerImageName.parse("registry:2"))
            .withExposedPorts(5000)

        init {
            registry.start()
            "set breakpoint here"
        }
    }

    @Test
    fun test() {
        
    }
}