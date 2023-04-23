package seven.belog.preasy.infrastructure.restapp

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import seven.belog.preasy.application.Service
import seven.belog.preasy.infrastructure.restapp.plugins.configureRouting
import seven.belog.preasy.infrastructure.restapp.plugins.configureSerialization

internal class Server(private val service: Service) {

    fun start(port: Int, host: String) {
        embeddedServer(
            factory = Netty,
            port = port,
            host = host,
            module = { module(service) }
        ).start(wait = true)
    }
}

private fun Application.module(service: Service) {
    configureSerialization()
    configureRouting(service)
}