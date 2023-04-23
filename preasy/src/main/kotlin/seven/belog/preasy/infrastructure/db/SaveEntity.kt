package seven.belog.preasy.infrastructure.db

import org.ktorm.schema.Table
import org.ktorm.schema.bytes
import org.ktorm.schema.text

internal object SaveEntity: Table<Nothing>(tableName = "save", schema = "preasy") {
    val id = text("id").primaryKey()
    val password = text("password")
    val file = bytes("file")
}