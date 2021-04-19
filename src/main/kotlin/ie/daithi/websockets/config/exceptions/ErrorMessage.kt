package ie.daithi.websockets.config.exceptions

import java.time.LocalDateTime

data class ErrorMessage(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String
)