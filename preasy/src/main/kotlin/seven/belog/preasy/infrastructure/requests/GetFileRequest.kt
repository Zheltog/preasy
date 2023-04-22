package seven.belog.preasy.infrastructure.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetFileRequest(
    val id: String,
    val password: String?
)