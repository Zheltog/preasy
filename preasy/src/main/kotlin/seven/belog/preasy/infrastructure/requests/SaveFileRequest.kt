package seven.belog.preasy.infrastructure.requests

import kotlinx.serialization.Serializable

@Serializable
data class SaveFileRequest(
    val file: String,
    val password: String?
)