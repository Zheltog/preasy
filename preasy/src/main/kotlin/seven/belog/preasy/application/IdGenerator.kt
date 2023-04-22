package seven.belog.preasy.application

import seven.belog.preasy.domain.FileId
import kotlin.math.absoluteValue
import kotlin.random.Random

fun interface IdGenerator {
    fun generateId(): FileId
}

fun numericIdGenerator(length: Int) = IdGenerator {
    val idValue = StringBuilder()
    (1..length).forEach { _ ->
        idValue.append((Random.nextInt().absoluteValue % 10).toString())
    }
    FileId(id = idValue.toString())
}