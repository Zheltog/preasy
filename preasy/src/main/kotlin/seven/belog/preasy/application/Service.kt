package seven.belog.preasy.application

import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.File
import seven.belog.preasy.domain.FileId

object Service {

    private data class FileAndPassword(
        val file: File,
        val password: Password?
    )

    private val saves = mutableMapOf<FileId, FileAndPassword>()

    private val idGenerator = numericIdGenerator(4)

    fun save(file: File, password: Password?): FileId {
        val id = idGenerator.generateId()
        saves[id] = FileAndPassword(
            file = file,
            password = password
        )
        return id
    }

    fun get(id: FileId, password: Password?): Result<File> {
        val found = saves[id]
            ?: return Result.failure(Exception("File not found"))

        if (found.password != password) {
            return Result.failure(Exception("Wrong password"))
        }

        return Result.success(found.file)
    }
}