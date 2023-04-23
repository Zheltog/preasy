package seven.belog.preasy.infrastructure

import org.slf4j.LoggerFactory
import java.util.Properties

internal object ConfigReader {

    const val DEFAULT_CONFIG_NAME = "preasy.properties"
    const val DEFAULT_SERVER_PORT = 8080
    const val DEFAULT_SERVER_HOST = "0.0.0.0"
    const val DEFAULT_SAVES_TTL_SECONDS = 86400L  // 24 hours
    const val DEFAULT_SAVES_CLEAN_INTERVAL_SECONDS = 600L;   // 10 minutes
    const val DEFAULT_DB_URL = "jdbc:postgresql://localhost:5432/preasy"
    const val DEFAULT_DB_USER = "postgres"
    const val DEFAULT_DB_PASSWORD = "postgres"

    private val logger = LoggerFactory.getLogger(ConfigReader::class.java)

    internal data class Config(
        val serverPort: Int,
        val serverHost: String,
        val savesTtlSeconds: Long,
        val savesCleanIntervalSeconds: Long,
        val dbUrl: String,
        val dbUser: String,
        val dbPassword: String
    )

    fun readConfig(fileName: String? = null): Config {
        val properties = Properties()
        val file = this::class.java.classLoader.getResourceAsStream(fileName ?: DEFAULT_CONFIG_NAME)
        properties.load(file)

        val serverPort = properties.getProperty("server.port")?.toInt()
        val serverHost = properties.getProperty("server.host")
        val savesTtlSeconds = properties.getProperty("saves.ttl.seconds")?.toLong()
        val savesCleanIntervalSeconds = properties.getProperty("saves.clean.interval.seconds")?.toLong()
        val dbUrl = properties.getProperty("db.url")
        val dbUser = properties.getProperty("db.user")
        val dbPassword = properties.getProperty("db.password")

        val config =  Config(
            serverPort = serverPort ?: DEFAULT_SERVER_PORT,
            serverHost = serverHost ?: DEFAULT_SERVER_HOST,
            savesTtlSeconds = savesTtlSeconds ?: DEFAULT_SAVES_TTL_SECONDS,
            savesCleanIntervalSeconds = savesCleanIntervalSeconds ?: DEFAULT_SAVES_CLEAN_INTERVAL_SECONDS,
            dbUrl = dbUrl ?: DEFAULT_DB_URL,
            dbUser = dbUser ?: DEFAULT_DB_USER,
            dbPassword = dbPassword ?: DEFAULT_DB_PASSWORD
        )

        logger.info("conig = {}", config)

        return config
    }
}