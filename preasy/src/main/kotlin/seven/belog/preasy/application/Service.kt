package seven.belog.preasy.application

import seven.belog.preasy.domain.Password
import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId

interface Service {
    fun save(file: ByteArray, password: Password?): Save
    fun get(id: SaveId, password: Password?): Result<ByteArray>
    fun delete(id: SaveId)
    fun validateFile(file: ByteArray?): Result<ByteArray>
}