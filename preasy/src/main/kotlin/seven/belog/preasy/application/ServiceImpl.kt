package seven.belog.preasy.application

import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId

class ServiceImpl(
    private val queryService: SaveQueryService
): Service {

    private val idGenerator = numericIdGenerator(4)

    override fun save(file: ByteArray, password: Password?): SaveId {
        val id = idGenerator.generateId()

        queryService.createSave(Save(
            id = id,
            password = password,
            file = file
        ))

        return id
    }

    override fun get(id: SaveId, password: Password?): Result<ByteArray> {
        val found = queryService.findSaveById(id)
            ?: return Result.failure(Exception("Save not found"))

        if (found.password != password) {
            return Result.failure(Exception("Wrong password"))
        }

        return Result.success(found.file)
    }

    override fun delete(id: SaveId) {
        queryService.deleteSave(id)
    }

    override fun validateFile(file: ByteArray?): Result<ByteArray> {
        file ?: return Result.failure(Exception("No file provided"))
        return Result.success(file)
    }
}