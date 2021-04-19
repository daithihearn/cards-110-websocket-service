package ie.daithi.websockets.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WsMessage(
    @JsonProperty("recipient") val recipient: String,
    @JsonProperty("message") val message: String
)