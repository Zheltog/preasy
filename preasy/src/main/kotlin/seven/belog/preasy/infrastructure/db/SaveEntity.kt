package seven.belog.preasy.infrastructure.db

import org.ktorm.schema.*

internal object SaveEntity: Table<Nothing>(tableName = "save", schema = "preasy") {
    val id = text("id").primaryKey()
    val password = text("password")
    val file = bytes("file")
    val expires = jdbcTimestamp("expires")
}