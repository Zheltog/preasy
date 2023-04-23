package seven.belog.preasy.infrastructure.db

import org.ktorm.database.Database
import org.ktorm.dsl.*
import seven.belog.preasy.application.SaveQueryService
import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId

internal class SaveQueryServiceImpl(
    private val database: Database
): SaveQueryService {

    override fun findSaveById(id: SaveId): Save? {
        val unvalidated = database
            .from(SaveEntity)
            .select(SaveEntity.id, SaveEntity.password, SaveEntity.file)
            .where { SaveEntity.id eq id.id }
            .map { row ->
                UnvalidatedSave(
                    id = row[SaveEntity.id],
                    password = row[SaveEntity.password],
                    file = row[SaveEntity.file]
                )
            }.firstOrNull()

        return saveOf(unvalidated)
    }

    override fun createSave(save: Save) {
        database.insert(SaveEntity) {
            set(it.id, save.id.id)
            set(it.password, save.password?.password)
            set(it.file, save.file)
        }
    }

    override fun deleteSave(id: SaveId) {
        database.delete(SaveEntity) {
            it.id eq id.id
        }
    }

    private data class UnvalidatedSave(
        val id: String?,
        val password: String?,
        val file: ByteArray?
    )

    private fun saveOf(unvalidated: UnvalidatedSave?): Save? {
        unvalidated ?: return null

        val id = unvalidated.id?.let { SaveId(it) }
            ?: throw Exception("id is not defined")

        val password = unvalidated.password?.let { Password(it) }

        val file = unvalidated.file
            ?: throw Exception("file is not defined")

        return Save(
            id = id,
            password = password,
            file = file
        )
    }
}