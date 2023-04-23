package seven.belog.preasy.infrastructure.restapp.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class SaveFileResponse(
    val id: String,
    val expiresAt: String
)