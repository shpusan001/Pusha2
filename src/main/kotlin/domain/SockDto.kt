package dekilla.core.domain

import java.io.Serializable

data class SockDto(
    val from:String,
    val command: String,
    val seperator: String,
    val data: String,
    val obj: Any?
) : Serializable