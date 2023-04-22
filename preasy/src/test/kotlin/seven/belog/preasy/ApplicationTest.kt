package seven.belog.preasy

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import seven.belog.preasy.infrastructure.plugins.configureRouting

class ApplicationTest {
    @Test
    fun testCheck() = testApplication {
        application {
            configureRouting()
        }
        client.get("/check").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
