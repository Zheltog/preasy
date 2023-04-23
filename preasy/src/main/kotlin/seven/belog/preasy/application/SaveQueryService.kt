package seven.belog.preasy.application

import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId
import java.time.LocalDateTime

interface SaveQueryService {
    fun findSaveById(id: SaveId): Save?
    fun createSave(save: Save)
    fun deleteSaveById(id: SaveId)
    fun deleteExpiredSaves(now: LocalDateTime)
}