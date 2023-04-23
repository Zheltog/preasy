package seven.belog.preasy.application

import seven.belog.preasy.domain.Save
import seven.belog.preasy.domain.SaveId

interface SaveQueryService {
    fun findSaveById(id: SaveId): Save?
    fun createSave(save: Save)
    fun deleteSave(id: SaveId)
}