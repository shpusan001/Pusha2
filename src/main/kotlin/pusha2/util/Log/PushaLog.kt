package pusha2.util.Log

import java.time.LocalDateTime

class PushaLog {
    companion object {

        var isActivate: Boolean = true

        fun log(log: String) {
            if (isActivate) {
                val localDateTime: LocalDateTime = LocalDateTime.now()
                println("[Pusha2] [${localDateTime}] " + log)
            }
        }
    }
}