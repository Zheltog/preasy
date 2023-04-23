package seven.belog.preasy.infrastructure.restapp.requests

import kotlinx.serialization.Serializable

@Serializable
internal data class GetFileRequest(
    val id: String,
    val password: String?
)