package seven.belog.preasy.infrastructure.db

import org.ktorm.database.Database
import org.ktorm.dsl.*
import seven.belog.preasy.application.SaveQueryService
import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId
import java.sql.Timestamp
import java.time.LocalDateTime

internal class SaveQueryServiceImpl(
    private val database: Database
): SaveQueryService {

    override fun findSaveById(id: SaveId): Save? {
        val unvalidated = database
            .from(SaveEntity)
            .select(SaveEntity.id, SaveEntity.password, SaveEntity.file, SaveEntity.expires)
            .where { SaveEntity.id eq id.value }
            .map { row ->
                UnvalidatedSave(
                    id = row[SaveEntity.id],
                    password = row[SaveEntity.password],
                    file = row[SaveEntity.file],
                    expires = row[SaveEntity.expires]
                )
            }.firstOrNull()

        return saveOf(unvalidated)
    }

    override fun createSave(save: Save) {
        database.insert(SaveEntity) {
            set(it.id, save.id.value)
            set(it.password, save.password?.value)
            set(it.file, save.file)
            set(it.expires, Timestamp.valueOf(save.expires))
        }
    }

    override fun deleteSaveById(id: SaveId) {
        database.delete(SaveEntity) {
            it.id eq id.value
        }
    }

    override fun deleteExpiredSaves(now: LocalDateTime) {
        database.delete(SaveEntity) {
            it.expires less Timestamp.valueOf(now)
        }
    }

    private data class UnvalidatedSave(
        val id: String?,
        val password: String?,
        val file: ByteArray?,
        val expires: Timestamp?
    )

    private fun saveOf(unvalidated: UnvalidatedSave?): Save? {
        unvalidated ?: return null

        val id = unvalidated.id?.let { SaveId(it) }
            ?: throw Exception("id is not defined")

        val password = unvalidated.password?.let { Password(it) }

        val file = unvalidated.file
            ?: throw Exception("file is not defined")

        val expires = unvalidated.expires
            ?: throw Exception("expires is not defined")

        return Save(
            id = id,
            password = password,
            file = file,
            expires = expires.toLocalDateTime()
        )
    }
}