package seven.belog.preasy.domain

@JvmInline
value class Password(
    val password: String
) {

    companion object {
        fun of(value: String?) = value?.let { Password(it) }
    }
}