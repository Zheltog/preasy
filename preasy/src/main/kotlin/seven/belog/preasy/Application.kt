package seven.belog.preasy

import org.ktorm.database.Database
import org.ktorm.support.postgresql.PostgreSqlDialect
import seven.belog.preasy.application.ServiceImpl
import seven.belog.preasy.infrastructure.ConfigReader
import seven.belog.preasy.infrastructure.db.SaveQueryServiceImpl
import seven.belog.preasy.infrastructure.OldSavesCleaner
import seven.belog.preasy.infrastructure.restapp.Server

fun main() {

    val config = ConfigReader.readConfig()

    val database = Database.connect(
        url = config.dbUrl,
        driver = "org.postgresql.Driver",
        dialect = PostgreSqlDialect(),
        user = config.dbUser,
        password = config.dbPassword
    )

    val queryService = SaveQueryServiceImpl(database)

    OldSavesCleaner(
        queryService = SaveQueryServiceImpl(database)
    ).launch(intervalSeconds = config.savesCleanIntervalSeconds)

    Server(service = ServiceImpl(
        saveTtlSeconds = config.savesTtlSeconds,
        queryService = queryService
    )).start(
        port = config.serverPort,
        host = config.serverHost
    )
}