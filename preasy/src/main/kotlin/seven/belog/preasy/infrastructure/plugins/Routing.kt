package seven.belog.preasy.infrastructure.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import seven.belog.preasy.application.Service
import seven.belog.preasy.domain.File
import seven.belog.preasy.domain.FileId
import seven.belog.preasy.domain.Password
import seven.belog.preasy.infrastructure.requests.GetFileRequest
import seven.belog.preasy.infrastructure.requests.SaveFileRequest

fun Application.configureRouting() {
    routing {
        checkHealth()
        saveFile()
        getFile()
    }
}

private fun Route.checkHealth() = get("/check") {
    call.respondText("Hello World!")
}

private fun Route.saveFile() = post("/save") {
    val request = call.receive<SaveFileRequest>()
    val fileId = Service.save(
        file = File(request.file),
        password = Password.of(request.password)
    )
    call.respondText(text = fileId.id, status = HttpStatusCode.OK)
}

private fun Route.getFile() = post("/get") {
    val request = call.receive<GetFileRequest>()
    val result = Service.get(
        id = FileId(id = request.id),
        password = Password.of(request.password)
    )
    result.onFailure {
        call.respondText(text = it.message ?: "", status = HttpStatusCode.BadRequest)
        return@post
    }
    result.onSuccess {
        call.respondText(text = it.file, status = HttpStatusCode.OK)
    }
}