package seven.belog.preasy

import org.ktorm.database.Database
import org.ktorm.support.postgresql.PostgreSqlDialect
import seven.belog.preasy.application.ServiceImpl
import seven.belog.preasy.infrastructure.db.SaveQueryServiceImpl
import seven.belog.preasy.infrastructure.db.OldSavesCleaner
import seven.belog.preasy.infrastructure.restapp.Server

fun main() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/preasy",
        driver = "org.postgresql.Driver",
        dialect = PostgreSqlDialect(),
        user = "postgres",
        password = "postgres"
    )

    val queryService = SaveQueryServiceImpl(database)

    OldSavesCleaner(
        queryService = SaveQueryServiceImpl(database)
    ).launch(intervalSeconds = 30)

    Server(service = ServiceImpl(
        saveTtlSeconds = 60L,
        queryService = queryService
    )).start(
        port = 8080,
        host = "0.0.0.0"
    )
}