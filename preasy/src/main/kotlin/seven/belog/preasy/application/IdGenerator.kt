package seven.belog.preasy.application

import seven.belog.preasy.domain.SaveId
import kotlin.math.absoluteValue
import kotlin.random.Random

fun interface IdGenerator {
    fun generateId(): SaveId
}

fun numericIdGenerator(length: Int) = IdGenerator {
    val idValue = StringBuilder()
    (1..length).forEach { _ ->
        idValue.append((Random.nextInt().absoluteValue % 10).toString())
    }
    SaveId(value = idValue.toString())
}