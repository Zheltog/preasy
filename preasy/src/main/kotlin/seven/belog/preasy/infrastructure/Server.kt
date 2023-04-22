package seven.belog.preasy.infrastructure

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import seven.belog.preasy.infrastructure.plugins.configureRouting
import seven.belog.preasy.infrastructure.plugins.configureSerialization

class Server {

    fun start() {
        embeddedServer(
            factory = Netty,
            port = 8080,
            host = "0.0.0.0",
            module = Application::module
        ).start(wait = true)
    }
}

private fun Application.module() {
    configureSerialization()
    configureRouting()
}