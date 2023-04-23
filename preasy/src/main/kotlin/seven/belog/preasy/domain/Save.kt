package seven.belog.preasy.domain

data class Save(
    val id: SaveId,
    val password: Password?,
    val file: ByteArray
)