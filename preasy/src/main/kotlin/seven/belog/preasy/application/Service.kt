package seven.belog.preasy.application

import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.FileId

object Service {

    private data class FileAndPassword(
        val file: ByteArray,
        val password: Password?
    )

    private val saves = mutableMapOf<FileId, FileAndPassword>()

    private val idGenerator = numericIdGenerator(4)

    fun save(file: ByteArray, password: Password?): FileId {
        val id = idGenerator.generateId()
        saves[id] = FileAndPassword(
            file = file,
            password = password
        )
        return id
    }

    fun get(id: FileId, password: Password?): Result<ByteArray> {
        val found = saves[id]
            ?: return Result.failure(Exception("File not found"))

        if (found.password != password) {
            return Result.failure(Exception("Wrong password"))
        }

        return Result.success(found.file)
    }

    fun deleteIfPresent(id: FileId) {
        saves.remove(id)
    }

    fun validateFile(file: ByteArray?): Result<ByteArray> {
        file ?: return Result.failure(Exception("No file provided"))
        return Result.success(file)
    }
}