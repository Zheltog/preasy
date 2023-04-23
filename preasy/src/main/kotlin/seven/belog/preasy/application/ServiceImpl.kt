package seven.belog.preasy.application

import org.slf4j.LoggerFactory
import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId
import java.time.LocalDateTime

class ServiceImpl(
    private val saveTtlSeconds: Long,
    private val queryService: SaveQueryService
): Service {

    private val logger = LoggerFactory.getLogger(ServiceImpl::class.java)

    private val idGenerator = numericIdGenerator(4)

    override fun save(file: ByteArray, password: Password?): Save {
        logger.info("saving new file...")

        val id = idGenerator.generateId()

        val save = Save(
            id = id,
            password = password,
            file = file,
            expires = LocalDateTime.now().plusSeconds(saveTtlSeconds)
        )

        queryService.createSave(save)

        logger.info("saved file, id = {}", save.id)

        return save
    }

    override fun get(id: SaveId, password: Password?): Result<ByteArray> {
        logger.info("getting file, id = {}", id)

        val found = queryService.findSaveById(id)
            ?: return Result.failure(Exception("Save not found"))

        if (found.password != password) {
            return Result.failure(Exception("Wrong password"))
        }

        return Result.success(found.file)
    }

    override fun delete(id: SaveId) {
        logger.info("deleting file, id = {}", id)
        queryService.deleteSaveById(id)
    }

    override fun validateFile(file: ByteArray?): Result<ByteArray> {
        file ?: return Result.failure(Exception("No file provided"))
        return Result.success(file)
    }
}