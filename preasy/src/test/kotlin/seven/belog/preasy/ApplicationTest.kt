package seven.belog.preasy

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import org.mockito.kotlin.mock
import seven.belog.preasy.infrastructure.restapp.plugins.configureRouting

class ApplicationTest {
    @Test
    fun testCheck() = testApplication {
        application {
            configureRouting(mock())
        }
        client.get("/check").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
