package seven.belog.preasy.domain

import java.time.LocalDateTime

data class Save(
    val id: SaveId,
    val password: Password?,
    val file: ByteArray,
    val expires: LocalDateTime
)