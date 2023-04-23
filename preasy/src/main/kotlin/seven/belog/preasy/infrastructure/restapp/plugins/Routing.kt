package seven.belog.preasy.infrastructure.restapp.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import seven.belog.preasy.application.Service
import seven.belog.preasy.domain.SaveId
import seven.belog.preasy.domain.Password
import seven.belog.preasy.infrastructure.restapp.requests.GetFileRequest

private const val PASSWORD_PART_NAME = "password"
private const val FILE_PART_NAME = "file"

internal fun Application.configureRouting(service: Service) {
    routing {
        checkHealth()
        saveFile(service)
        getFile(service)
        deleteFile(service)
    }
}

private fun Route.checkHealth() = get("/check") {
    call.respondText("Hello World!")
}

private fun Route.saveFile(service: Service) = post("/save") {
    var password: Password? = null
    var file: ByteArray? = null

    call.receiveMultipart().forEachPart { part ->
        when (part) {
            is PartData.FileItem -> {
                file = part.streamProvider().readBytes()
            }
            is PartData.FormItem -> {
                if (part.name == PASSWORD_PART_NAME) {
                    password = Password.of(part.value)
                }
            }
            else -> {}
        }
        part.dispose()
    }

    val validatedFile = service.validateFile(file).getOrElse {
        call.respondText(
            text = it.message ?: "",
            status = HttpStatusCode.BadRequest
        )
        return@post
    }


    val fileId = service.save(
        file = validatedFile,
        password = password
    )

    call.respondText(text = fileId.id, status = HttpStatusCode.OK)
}

private fun Route.getFile(service: Service) = post("/get") {
    val request = call.receive<GetFileRequest>()
    val result = service.get(
        id = SaveId(id = request.id),
        password = Password.of(request.password)
    )
    result.onFailure {
        call.respondText(text = it.message ?: "", status = HttpStatusCode.BadRequest)
        return@post
    }
    result.onSuccess {
        call.respondBytes(bytes = it, status = HttpStatusCode.OK)
    }
}

private fun Route.deleteFile(service: Service) = delete("/delete/{id}") {
    val id = call.parameters["id"]

    if (id == null) {
        call.respondText(text = "Bad request", status = HttpStatusCode.BadRequest)
        return@delete
    }

    service.delete(SaveId(id))
}