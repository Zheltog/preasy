package seven.belog.preasy.infrastructure

import org.slf4j.LoggerFactory
import seven.belog.preasy.application.SaveQueryService
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class OldSavesCleaner(
    private val queryService: SaveQueryService
) {

    private val logger = LoggerFactory.getLogger(OldSavesCleaner::class.java)

    fun launch(intervalSeconds: Long) {
        val scheduler = Executors.newScheduledThreadPool(1)
        val task = Runnable { clean() }
        scheduler.scheduleWithFixedDelay(task, 0, intervalSeconds, TimeUnit.SECONDS)
    }

    private fun clean() {
        val now = LocalDateTime.now()
        logger.info("deleting saves expired before {}...", now)
        queryService.deleteExpiredSaves(now)
    }
}